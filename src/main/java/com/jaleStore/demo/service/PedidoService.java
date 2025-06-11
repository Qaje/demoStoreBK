package com.jaleStore.demo.service;

import com.jaleStore.demo.dto.PedidoDTO;
import com.jaleStore.demo.dto.Response.DatosEntregaDTO;
import com.jaleStore.demo.entity.*;
import com.jaleStore.demo.exception.CarritoVacioException;
import com.jaleStore.demo.exception.StockInsuficienteException;
import com.jaleStore.demo.exception.UsuarioNoEncontradoException;
import com.jaleStore.demo.repository.*;
import com.jaleStore.demo.util.EstadoPedido;
import com.jaleStore.demo.util.NivelCliente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private QRService qrService;


    public PedidoDTO crearPedidoDesdeCarrito(Long usuarioId, DatosEntregaDTO datosEntrega) {
        // 1. Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId));

        // 2. Obtener items del carrito del usuario
        List<CarritoItem> itemsCarrito = carritoRepository.findByUsuarioIdAndActivoTrue(usuarioId);

        if (itemsCarrito.isEmpty()) {
            throw new CarritoVacioException("El carrito está vacío para el usuario: " + usuarioId);
        }

        // 3. Validar stock disponible para todos los productos
        validarStockDisponible(itemsCarrito);

        // 4. Generar número de pedido único
        String numeroPedido = generarNumeroPedidoUnico();

        BigDecimal subtotal = calcularSubtotal(itemsCarrito);
        BigDecimal descuentoMayorista = calcularDescuentoMayorista(itemsCarrito, usuario);
        BigDecimal total = subtotal.subtract(descuentoMayorista);

        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(numeroPedido);
        pedido.setUsuario(usuario);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setSubTotal(pedido.getSubTotal());
        pedido.setDescuentoMayorista(descuentoMayorista);
        pedido.setTotal(total);

//        datos de entrega
        pedido.setDireccionEntrega(datosEntrega.getDireccion());
        pedido.setTelefonoEntrega(datosEntrega.getTelefono());
        pedido.setNotasEntrega(datosEntrega.getNotasEspeciales());

//        guardar pedido
        pedido = pedidoRepository.save(pedido);

//        crear detaller del pedido y reducir stock
        crearDetallesPedidoYReducirStock(pedido,itemsCarrito);

//        limpiar carrito del Usuario
        limpiarCarritoUsuario(usuarioId);

//        generar qr del pago
        String qrPago = qrService.generarQRPago(pedido);
        pedido.setCodigoQR(qrPago);
        pedidoRepository.save(pedido);

        return convertirPedidoADTO(pedido);
    }

    public String generarQRPago(Long pedidoId) {
        try {
//            obtener datos del partido
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(()-> new RuntimeException("Pedido no encontrado"));
            //crear un URL/datos de pago segun la pasarela
            String datosQR = crearDatosPago(pedido);

            return generarQRPago(Long.valueOf(datosQR));
        }catch (Exception e){
            throw new RuntimeException("Error al generar QR de pago:"+e.getMessage());
        }
    }

    public void confirmarPago(String transaccionId, Long pedidoId) {
        // Actualizar estado del pedido a PAGADO
        // Enviar confirmación al cliente
    }

    ///////////////////////////////////////////////////////////////////////////////
//    valida que hay suficientes stock para todos los productos del carrito
    private void validarStockDisponible(List<CarritoItem> itemsCarrito) {
        for (CarritoItem item : itemsCarrito) {
            Producto producto = item.getProducto();

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                                producto.getNombre(), producto.getStock(), item.getCantidad())
                );
            }

            if (!producto.getActivo()) {
                throw new StockInsuficienteException("El producto " + producto.getNombre() + " ya no está disponible");
            }
        }
    }

    private String generarNumeroPedidoUnico(){
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().substring(0,0).toUpperCase();
        return "PED-" + timestamp.substring(timestamp.length() - 6)+ "-" + uuid;
    }

//    Calcula el subtaotal del pedido
    private BigDecimal calcularSubtotal(List<CarritoItem> itemsCarrito){
        return itemsCarrito.stream()
                .map(item -> {
                    Producto producto = item.getProducto();
                    BigDecimal precio = item.getEsVentaMayorista()?
                            producto.getPrecioMayor():
                            producto.getPrecioUnidad();
                    return precio.multiply(BigDecimal.valueOf(item.getCantidad()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

//     Calcula descuento adicional para usuarios mayoristas

    private BigDecimal calcularDescuentoMayorista(List<CarritoItem> itemsCarrito, Usuario usuario) {
        if (!usuario.getRol().equals(NivelCliente.MAYORISTA)) {
            return BigDecimal.ZERO;
        }

        // Aplicar descuento adicional del 5% para mayoristas en compras grandes
        BigDecimal subtotal = calcularSubtotal(itemsCarrito);
        BigDecimal minimoParaDescuento = new BigDecimal("1000.00");

        if (subtotal.compareTo(minimoParaDescuento) >= 0) {
            return subtotal.multiply(new BigDecimal("0.05")); // 5% descuento
        }

        return BigDecimal.ZERO;
    }

    /**
     * Crea los detalles del pedido y reduce el stock de productos
     */
    private void crearDetallesPedidoYReducirStock(Pedido pedido, List<CarritoItem> itemsCarrito) {
        for (CarritoItem item : itemsCarrito) {
            // Crear detalle del pedido
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getEsVentaMayorista() ?
                    item.getProducto().getPrecioMayor() :
                    item.getProducto().getPrecioUnidad());
            detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())));
            detalle.setEsVentaMayorista(item.getEsVentaMayorista());

            detallePedidoRepository.save(detalle);

            // Reducir stock del producto
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());

            // Si el stock llega a 0, marcar como no activo
            if (producto.getStock() <= 0) {
                producto.setActivo(false);
            }

            productoRepository.save(producto);
        }
    }

    private void limpiarCarritoUsuario(Long usuarioId) {
        List<CarritoItem> itemsCarrito = carritoRepository.findByUsuarioIdAndActivoTrue(usuarioId);

        for (CarritoItem item : itemsCarrito) {
            item.setActivo(false);
            item.setFechaEliminacion(LocalDateTime.now());
        }

        carritoRepository.saveAll(itemsCarrito);
    }

    private PedidoDTO convertirPedidoADTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setNumeroPedido(pedido.getNumeroPedido());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setSubTotal(pedido.getSubTotal());
        dto.setDescuentoMayorista(pedido.getDescuentoMayorista());
        dto.setTotal(pedido.getTotal());
        dto.setQrPago(pedido.getCodigoQR());

        // Datos de entrega
        dto.setDireccionEntrega(pedido.getDireccionEntrega());
        dto.setTelefonoEntrega(pedido.getTelefonoEntrega());
        //dto.setNombreReceptor(pedido.getNombreReceptor());
        dto.setNotasEntrega(pedido.getNotasEntrega());

        // Datos del usuario
        dto.setUsuario(pedido.getUsuario());
//        dto.setNombreUsuario(pedido.getUsuario().getNombre());
//        dto.setEmailUsuario(pedido.getUsuario().getEmail());

        return dto;
    }

    private String crearDatosPago(Pedido pedido){
        // OPCIÓN 1: URL simple de pago
        return crearUrlPagoSimple(pedido);

        // OPCIÓN 2: Integración con MercadoPago (descomenta para usar)
        // return crearPagoMercadoPago(pedido);

        // OPCIÓN 3: Datos bancarios/transferencia (descomenta para usar)
        // return crearDatosTransferencia(pedido);
    }

    private String crearUrlPagoSimple(Pedido pedido){
        return String.format("%s/pagar?pedido=%d&monto=%.2f&token=%s",
                baseUrl,
                pedido.getId(),
                pedido.getTotal(),
                generarTokenSeguro(pedido)
                );
    }

    private String generarTokenSeguro(Pedido pedido){
        String data = pedido.getId() + ":" + pedido.getTotal() + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(data.getBytes()).substring(0,16);
    }
}