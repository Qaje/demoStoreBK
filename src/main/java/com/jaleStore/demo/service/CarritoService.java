package com.jaleStore.demo.service;

import com.jaleStore.demo.dto.Mapper.CarritoMapper;
import com.jaleStore.demo.dto.Response.CarritoDTO;
import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.CarritoItem;
import com.jaleStore.demo.entity.ProductoVariante;
import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.exception.StockInsuficienteException;
import com.jaleStore.demo.repository.CarritoRepository;
import com.jaleStore.demo.repository.ProductoVarianteRepository;
import com.jaleStore.demo.repository.UsuarioRepository;
import com.jaleStore.demo.util.TipoVenta;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoVarianteRepository varianteRepository;

    @Autowired
    private ProductoService productoService;


    public CarritoDTO agregarVariante(Long usuarioId, Long varianteId, Integer cantidad) {
        // Verificar que la variante existe y tiene stock
        ProductoVariante productoVariante = varianteRepository.findById(varianteId)
                .orElseThrow(() -> new EntityNotFoundException("Producto variante no encontrada"));

        if (!productoService.verificarStock(varianteId, cantidad)) {
            throw new StockInsuficienteException("Stock insuficiente para producto variante seleccionada");
        }

        // Obtener o crear carrito - CORREGIDO: usar método consistente
        Carrito carrito = obtenerOCrearCarritoActivo(usuarioId);

        // CORREGIDO: Buscar si ya existe esta variante en el carrito
        // Nota: CarritoItem tiene relación con Producto, no con ProductoVariante
        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoVariante.getProducto().getId()))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            // Actualizar cantidad
            Integer nuevaCantidad = itemExistente.getCantidad() + cantidad;
            if (!productoService.verificarStock(varianteId, nuevaCantidad)) {
                throw new StockInsuficienteException("Stock insuficiente para la cantidad total solicitada");
            }
            itemExistente.setCantidad(nuevaCantidad);
        } else {
            // CORREGIDO: Crear nuevo item con la estructura correcta
            CarritoItem nuevoItem = new CarritoItem();
            // CarritoItem no tiene setCarrito, se maneja por la relación
            nuevoItem.setUsuario(carrito.getUsuario()); // CarritoItem requiere usuario
            nuevoItem.setProducto(productoVariante.getProducto()); // Usar el producto de la variante
            nuevoItem.setCantidad(cantidad);

            // Determinar tipo de venta y precio
            TipoVenta tipoVenta = determinarTipoVenta(carrito, cantidad);
            BigDecimal precio = tipoVenta == TipoVenta.MAYORISTA ?
                    productoVariante.getProducto().getPrecioMayor() :
                    productoVariante.getProducto().getPrecioUnidad();

            // CORREGIDO: CarritoItem usa esVentaMayorista en lugar de tipoVenta
            nuevoItem.setEsVentaMayorista(tipoVenta == TipoVenta.MAYORISTA);
            nuevoItem.setPrecioUnitario(precio);

            carrito.getItems().add(nuevoItem);
        }

        carrito.setFechaActualizacion(LocalDateTime.now());
        carrito = carritoRepository.save(carrito);

        return CarritoMapper.toDTO(carrito);
    }

    private TipoVenta determinarTipoVenta(Carrito carrito, Integer cantidadNueva) {
        Integer totalItems = carrito.getItems().stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum() + cantidadNueva;

        // Si el total supera la cantidad mayorista, aplicar precio mayorista
        return totalItems >= 12 ? TipoVenta.MAYORISTA : TipoVenta.UNITARIA;
    }

    // CORREGIDO: Método para crear carrito con usuario completo
    private Carrito crearNuevoCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setActivo(true); // Asegurar que el carrito está activo
        carrito.setFechaCreacion(LocalDateTime.now());
        carrito.setFechaActualizacion(LocalDateTime.now());

        return carritoRepository.save(carrito);
    }

    // CORREGIDO: Método consistente para obtener carrito activo
    public Carrito obtenerOCrearCarritoActivo(Long usuarioId) {
        return carritoRepository.findCarritoActivoByUsuarioId(usuarioId)
                .orElseGet(() -> crearNuevoCarrito(usuarioId));
    }

    // CORREGIDO: Método para obtener cualquier carrito (activo o no)
    public Carrito obtenerOCrearCarrito(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> crearNuevoCarrito(usuarioId));
    }

    // Método público para obtener carrito
    public Carrito obtenerCarrito(Long usuarioId) {
        return obtenerOCrearCarritoActivo(usuarioId);
    }

    // CORREGIDO: Método para obtener carrito existente sin crear uno nuevo
    public Carrito obtenerCarritoExistente(Long usuarioId) {
        return carritoRepository.findCarritoActivoByUsuarioId(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No hay carrito activo para el usuario"));
    }

    // Agregar estos métodos al CarritoService.java existente

    /**
     * Actualiza la cantidad de un item específico en el carrito
     */
//    public CarritoDTO actualizarCantidadItem(Long usuarioId, Long itemId, Integer nuevaCantidad) {
//        // Obtener carrito activo
//        Carrito carrito = obtenerCarritoExistente(usuarioId);
//
//        // Buscar el item específico
//        CarritoItem item = carrito.getItems().stream()
//                .filter(i -> i.getId().equals(itemId) && i.getActivo())
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el carrito"));
//
//        // Verificar que el item pertenece al usuario
//        if (!item.getUsuario().equals(usuarioId)) {
//            throw new RuntimeException("No tienes permisos para modificar este item");
//        }
//
//        // Verificar stock disponible para la nueva cantidad
//        // Nota: Necesitarías obtener el ProductoVariante correspondiente
//        // Por ahora asumo que tienes una forma de obtener la variante del producto
//        if (!productoService.verificarStock(item.getProducto().getId(), nuevaCantidad)) {
//            throw new StockInsuficienteException("Stock insuficiente para la cantidad solicitada");
//        }
//
//        // Actualizar cantidad
//        item.setCantidad(nuevaCantidad);
//
//        // Recalcular precios si es necesario
//        recalcularPreciosCarrito(carrito);
//
//        carrito.setFechaActualizacion(LocalDateTime.now());
//        carrito = carritoRepository.save(carrito);
//
//        return CarritoMapper.toDTO(carrito);
//    }
    public CarritoDTO actualizarCantidadItem(Long usuarioId, Long itemId, Integer nuevaCantidad) {
        // Obtener carrito activo
        Carrito carrito = obtenerCarritoExistente(usuarioId);

        // Buscar el item específico
        CarritoItem item = carrito.getItems().stream()
                .filter(i -> i.getId().equals(itemId) && i.getActivo())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el carrito"));

        // Verificar que el item pertenece al usuario - CORREGIDO
        if (!verificarPermisoItem(item, usuarioId)) {
            throw new RuntimeException("No tienes permisos para modificar este item");
        }

        // Verificar stock disponible para la nueva cantidad
        if (!productoService.verificarStock(item.getProducto().getId(), nuevaCantidad)) {
            throw new StockInsuficienteException("Stock insuficiente para la cantidad solicitada");
        }

        // Actualizar cantidad
        item.setCantidad(nuevaCantidad);

        // Recalcular precios si es necesario
        recalcularPreciosCarrito(carrito);

        carrito.setFechaActualizacion(LocalDateTime.now());
        carrito = carritoRepository.save(carrito);

        return CarritoMapper.toDTO(carrito);
    }

    /**
     * Elimina un item específico del carrito
     */
//    public CarritoDTO eliminarItem(Long usuarioId, Long itemId) {
//        // Obtener carrito activo
//        Carrito carrito = obtenerCarritoExistente(usuarioId);
//
//        // Buscar el item específico
//        CarritoItem item = carrito.getItems().stream()
//                .filter(i -> i.getId().equals(itemId) && i.getActivo())
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el carrito"));
//
//        // Verificar que el item pertenece al usuario
//        if (!item.getUsuario().equals(usuarioId)) {
//            throw new RuntimeException("No tienes permisos para eliminar este item");
//        }
//
//        // Marcar como inactivo en lugar de eliminar físicamente
//        item.setActivo(false);
//        item.setFechaEliminacion(LocalDateTime.now());
//
//        // Recalcular precios del carrito restante
//        recalcularPreciosCarrito(carrito);
//
//        carrito.setFechaActualizacion(LocalDateTime.now());
//        carrito = carritoRepository.save(carrito);
//
//        return CarritoMapper.toDTO(carrito);
//    }
    public CarritoDTO eliminarItem(Long usuarioId, Long itemId) {
        // Obtener carrito activo
        Carrito carrito = obtenerCarritoExistente(usuarioId);

        // Buscar el item específico
        CarritoItem item = carrito.getItems().stream()
                .filter(i -> i.getId().equals(itemId) && i.getActivo())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el carrito"));

        // Verificar que el item pertenece al usuario - CORREGIDO
        if (!verificarPermisoItem(item, usuarioId)) {
            throw new RuntimeException("No tienes permisos para eliminar este item");
        }

        // Marcar como inactivo en lugar de eliminar físicamente
        item.setActivo(false);
        item.setFechaEliminacion(LocalDateTime.now());

        // Recalcular precios del carrito restante
        recalcularPreciosCarrito(carrito);

        carrito.setFechaActualizacion(LocalDateTime.now());
        carrito = carritoRepository.save(carrito);

        return CarritoMapper.toDTO(carrito);
    }

    /**
     * Recalcula los precios mayoristas del carrito
     */
    public CarritoDTO recalcularPreciosMayoristas(Long usuarioId) {
        Carrito carrito = obtenerCarritoExistente(usuarioId);

        recalcularPreciosCarrito(carrito);

        carrito.setFechaActualizacion(LocalDateTime.now());
        carrito = carritoRepository.save(carrito);

        return CarritoMapper.toDTO(carrito);
    }

    /**
     * Vacía completamente el carrito del usuario
     */
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoExistente(usuarioId);

        // Marcar todos los items como inactivos
        carrito.getItems().forEach(item -> {
            if (item.getActivo()) {
                item.setActivo(false);
                item.setFechaEliminacion(LocalDateTime.now());
            }
        });

        carrito.setFechaActualizacion(LocalDateTime.now());
        carritoRepository.save(carrito);
    }

    /**
     * Método privado para recalcular precios del carrito
     */
    private void recalcularPreciosCarrito(Carrito carrito) {
        // Obtener total de items activos
        Integer totalItems = carrito.getItems().stream()
                .filter(CarritoItem::getActivo)
                .mapToInt(CarritoItem::getCantidad)
                .sum();

        boolean esMayorista = totalItems >= 12;

        // Actualizar precios de todos los items activos
        carrito.getItems().stream()
                .filter(CarritoItem::getActivo)
                .forEach(item -> {
                    BigDecimal nuevoPrecio = esMayorista ?
                            item.getProducto().getPrecioMayor() :
                            item.getProducto().getPrecioUnidad();

                    item.setPrecioUnitario(nuevoPrecio);
                    item.setEsVentaMayorista(esMayorista);
                });
    }

    public List<Carrito> getCarritosActivosByUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioIdAndActivo(usuarioId, true);
    }

    // O si prefieres usar la entidad Usuario:
    public List<Carrito> getCarritosActivosByUsuario(Usuario usuario) {
        return carritoRepository.findByUsuarioAndActivo(usuario, true);
    }

    private boolean verificarPermisoItem(CarritoItem item, Long usuarioId) {
        return item.getUsuario().getId().equals(usuarioId);
    }

}