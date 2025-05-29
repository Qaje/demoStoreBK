package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoVenta;

import java.math.BigDecimal;

public class ItemCarritoDTO {
    private Long id;
    private ProductoVarianteResumeDTO variante;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private TipoVenta tipoVenta;

    // getters y setters
}
