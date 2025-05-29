package com.jaleStore.demo.service;

import com.jaleStore.demo.dto.Response.CarritoDTO;
import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.ItemCarrito;
import com.jaleStore.demo.entity.ProductoVariante;
import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.repository.CarritoRepository;
import com.jaleStore.demo.repository.ProductoVarianteRepository;
import com.jaleStore.demo.util.TipoVenta;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoVarianteRepository varianteRepository;

    @Autowired
    private ProductoService productoService;

    public CarritoDTO agregarVariante(Long usuarioId, Long varianteId, Integer cantidad) {
        // Verificar que la variante existe y tiene stock
        ProductoVariante variante = varianteRepository.findById(varianteId)
                .orElseThrow(() -> new EntityNotFoundException("Variante no encontrada"));

        if (!productoService.verificarStock(varianteId, cantidad)) {
            throw new StockInsuficienteException("Stock insuficiente para la variante seleccionada");
        }

        // Obtener o crear carrito
        Carrito carrito = carritoRepository.findByUsuarioIdAndActivo(usuarioId, true)
                .orElse(crearNuevoCarrito(usuarioId));

        // Buscar si ya existe esta variante en el carrito
        ItemCarrito itemExistente = carrito.getItems().stream()
                .filter(item -> item.getVariante().getId().equals(varianteId))
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
            // Crear nuevo item
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setVariante(variante);
            nuevoItem.setCantidad(cantidad);

            // Determinar tipo de venta y precio
            TipoVenta tipoVenta = determinarTipoVenta(carrito, cantidad);
            BigDecimal precio = tipoVenta == TipoVenta.MAYORISTA ?
                    variante.getProducto().getPrecioMayor() :
                    variante.getProducto().getPrecioUnidad();

            nuevoItem.setTipoVenta(tipoVenta);
            nuevoItem.setPrecioUnitario(precio);

            carrito.getItems().add(nuevoItem);
        }

        carrito.setFechaActualizacion(LocalDateTime.now());
        carrito = carritoRepository.save(carrito);

        return CarritoMapper.toDTO(carrito);
    }

    private TipoVenta determinarTipoVenta(Carrito carrito, Integer cantidadNueva) {
        Integer totalItems = carrito.getItems().stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum() + cantidadNueva;

        // Si el total supera la cantidad mayorista, aplicar precio mayorista
        return totalItems >= 12 ? TipoVenta.MAYORISTA : TipoVenta.UNITARIA;
    }

    private Carrito crearNuevoCarrito(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(LocalDateTime.now());
        carrito.setFechaActualizacion(LocalDateTime.now());

        return carrito;
    }
}