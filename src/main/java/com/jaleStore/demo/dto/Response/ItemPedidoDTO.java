package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoVenta;

import java.math.BigDecimal;

public class ItemPedidoDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private String marca;
    private String modelo;
    private String talla;
    private String color;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal; // precioUnitario * cantidad
    private TipoVenta tipoVenta; // UNITARIO, MAYOREO
    private String imagenProducto;
    private Boolean disponible;
    private Integer stockDisponible;

    // Constructores, getters y setters
}
