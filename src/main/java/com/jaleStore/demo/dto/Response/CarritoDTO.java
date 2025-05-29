package com.jaleStore.demo.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CarritoDTO {
    private Long id;
    private List<ItemCarritoDTO> items;
    private BigDecimal subtotal;
    private BigDecimal descuentoMayorista;
    private BigDecimal total;
    private Integer totalItems;
    private Boolean aplicaMayorista;
    private LocalDateTime fechaActualizacion;

    // getters y setters
}