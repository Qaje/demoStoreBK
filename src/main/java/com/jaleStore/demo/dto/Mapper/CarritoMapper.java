package com.jaleStore.demo.dto.Mapper;

import com.jaleStore.demo.util.TipoVenta;
import com.jaleStore.demo.dto.Response.CarritoDTO;
import com.jaleStore.demo.dto.Response.ItemCarritoDTO;
import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.CarritoItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoMapper {

    public static CarritoDTO toDTO(Carrito carrito) {
        if (carrito == null) {
            return null;
        }

        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario() != null ? carrito.getUsuario().getId() : null);
        dto.setFechaCreacion(carrito.getFechaCreacion());
        dto.setFechaActualizacion(carrito.getFechaActualizacion());
        dto.setActivo(carrito.getActivo());

        // Mapear items del carrito
        if (carrito.getItems() != null && !carrito.getItems().isEmpty()) {
            List<ItemCarritoDTO> itemsDTO = carrito.getItems().stream()
                    .map(CarritoMapper::itemToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }

        // Calcular total
        dto.setTotal(calcularTotal(carrito));

        return dto;
    }

    public static ItemCarritoDTO itemToDTO(CarritoItem item) {
        if (item == null) {
            return null;
        }

        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setId(item.getId());
        dto.setCantidad(item.getCantidad());

        // Convertir Boolean esVentaMayorista a enum TipoVenta
        if (item.getEsVentaMayorista() != null && item.getEsVentaMayorista()) {
            dto.setTipoVenta(TipoVenta.MAYORISTA);
        } else {
            dto.setTipoVenta(TipoVenta.UNITARIA);
        }

        // Obtener precio unitario
        BigDecimal precioUnitario = obtenerPrecioUnitario(item);
        dto.setPrecioUnitario(precioUnitario);

        // Calcular subtotal
        if (precioUnitario != null && item.getCantidad() != null) {
            dto.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad())));
        } else {
            dto.setSubtotal(BigDecimal.ZERO);
        }

        // Mapear información del producto (ya que no hay variante)
        if (item.getProducto() != null) {
            // Nota: ItemCarritoDTO espera un ProductoVarianteResumeDTO
            // Tendrás que crear este objeto o modificar el DTO
            // Por ahora, establecemos los datos básicos que podemos

            // Si tienes un método setNombreProducto en ItemCarritoDTO:
            // dto.setNombreProducto(item.getProducto().getNombre());

            // Si tienes un método setProductoId en ItemCarritoDTO:
            // dto.setProductoId(item.getProducto().getId());
        }

        return dto;
    }

    private static BigDecimal obtenerPrecioUnitario(CarritoItem item) {
        // Opción 1: Si CarritoItem tiene precioUnitario guardado
        if (item.getPrecioUnitario() != null) {
            return item.getPrecioUnitario();
        }

        // Opción 2: Calcular desde el producto según tipo de venta
        if (item.getProducto() != null) {
            if (item.getEsVentaMayorista() != null && item.getEsVentaMayorista()) {
                // Venta mayorista
                return item.getProducto().getPrecioMayor();
            } else {
                // Venta unitaria (por defecto)
                return item.getProducto().getPrecioUnidad();
            }
        }

        // Fallback: precio cero
        return BigDecimal.ZERO;
    }

    private static BigDecimal calcularTotal(Carrito carrito) {
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return carrito.getItems().stream()
                .filter(item -> item != null && item.getActivo())
                .map(item -> {
                    BigDecimal precio = obtenerPrecioUnitario(item);
                    Integer cantidad = item.getCantidad() != null ? item.getCantidad() : 0;
                    return precio.multiply(BigDecimal.valueOf(cantidad));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}