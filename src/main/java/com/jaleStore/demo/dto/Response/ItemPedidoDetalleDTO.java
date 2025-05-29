package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoVenta;

import java.math.BigDecimal;

public class ItemPedidoDetalleDTO {
    private Long id;
    private String nombreProducto;
    private String marcaProducto;
    private Double talla;
    private String color;
    private String sku;
    private String imagenProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private TipoVenta tipoVenta;

    // getters y setters
}