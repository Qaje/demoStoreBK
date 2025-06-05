package com.jaleStore.demo.dto;

import com.jaleStore.demo.util.TipoVenta;

import java.math.BigDecimal;

public class ItemPedidoDTO {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private TipoVenta tipoVenta;

    // Información del producto/variante (solo datos necesarios)
    private ProductoVarianteBasicoDTO variante;

    // Constructores
    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(Long id, Integer cantidad, BigDecimal precioUnitario,
                         BigDecimal subtotal, TipoVenta tipoVenta) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.tipoVenta = tipoVenta;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public TipoVenta getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(TipoVenta tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public ProductoVarianteBasicoDTO getVariante() {
        return variante;
    }

    public void setVariante(ProductoVarianteBasicoDTO variante) {
        this.variante = variante;
    }
}
