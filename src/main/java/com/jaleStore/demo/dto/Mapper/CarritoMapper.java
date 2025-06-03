package com.jaleStore.demo.dto.Mapper;

import com.jaleStore.demo.dto.Response.CarritoDTO;
import com.jaleStore.demo.dto.Response.ItemCarritoDTO;
import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.ItemCarrito;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoMapper {

    public static CarritoDTO toDTO(Carrito carrito) {
        if (carrito == null) {
            return null;
        }

        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario().getId());
        dto.setFechaCreacion(carrito.getFechaCreacion());
        dto.setFechaActualizacion(carrito.getFechaActualizacion());
        dto.setActivo(carrito.getActivo());

        // Mapear items del carrito
        if (carrito.getItems() != null) {
            List<ItemCarritoDTO> itemsDTO = carrito.getItems().stream()
                    .map(CarritoMapper::itemToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }

        // Calcular total
        dto.setTotal(calcularTotal(carrito));

        return dto;
    }

    public static ItemCarritoDTO itemToDTO(ItemCarrito item) {
        if (item == null) {
            return null;
        }

        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setId(item.getId());
//        dto.setVarianteId(item.getVariante().getId());
//        dto.setNombreProducto(item.getVariante().getProducto().getNombre());
//        dto.setVarianteNombre(item.getVariante().getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setTipoVenta(item.getTipoVenta());
        dto.setSubtotal(item.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(item.getCantidad())));

        return dto;
    }

    private static java.math.BigDecimal calcularTotal(Carrito carrito) {
        return carrito.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(item.getCantidad())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
