package com.jaleStore.demo.dto.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarritoDTO {
    private Long id;
    private Long usuarioId;
    private List<ItemCarritoDTO> items;
    private BigDecimal subtotal;
    private BigDecimal descuentoMayorista;
    private BigDecimal total;
    private Integer totalItems;
    private Boolean aplicaMayorista;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean activo;

    // getters y setters
}