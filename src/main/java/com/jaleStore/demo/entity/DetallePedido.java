package com.jaleStore.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "detalle_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "es_venta_mayorista", nullable = false)
    private Boolean esVentaMayorista = false;

    // Información adicional del producto al momento de la compra
    @Column(name = "nombre_producto", length = 255)
    private String nombreProducto;

    @Column(name = "codigo_producto", length = 100)
    private String codigoProducto;

    @Column(name = "marca_producto", length = 100)
    private String marcaProducto;

    @Column(name = "talla_producto", length = 50)
    private String tallaProducto;

    @Column(name = "color_producto", length = 100)
    private String colorProducto;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    // Constructores
    public DetallePedido() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public DetallePedido(Pedido pedido, Producto producto, Integer cantidad,
                         BigDecimal precioUnitario, Boolean esVentaMayorista) {
        this();
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.esVentaMayorista = esVentaMayorista;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

        // Guardar información del producto al momento de la compra
        if (producto != null) {
            this.nombreProducto = producto.getNombre();
            this.codigoProducto = producto.getCodigo();
            this.marcaProducto = producto.getMarca();
            this.tallaProducto = producto.getTalla();
            this.colorProducto = producto.getColor();
        }
    }

    // Método para calcular subtotal automáticamente
    public void calcularSubtotal() {
        if (this.precioUnitario != null && this.cantidad != null) {
            this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        }
    }

    // Método para actualizar información del producto
    public void actualizarInfoProducto() {
        if (this.producto != null) {
            this.nombreProducto = producto.getNombre();
            this.codigoProducto = producto.getCodigo();
            this.marcaProducto = producto.getMarca();
            this.tallaProducto = producto.getTalla();
            this.colorProducto = producto.getColor();
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        // Actualizar información del producto automáticamente
        actualizarInfoProducto();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        // Recalcular subtotal automáticamente
        calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        // Recalcular subtotal automáticamente
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Boolean getEsVentaMayorista() {
        return esVentaMayorista;
    }

    public void setEsVentaMayorista(Boolean esVentaMayorista) {
        this.esVentaMayorista = esVentaMayorista;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public String getTallaProducto() {
        return tallaProducto;
    }

    public void setTallaProducto(String tallaProducto) {
        this.tallaProducto = tallaProducto;
    }

    public String getColorProducto() {
        return colorProducto;
    }

    public void setColorProducto(String colorProducto) {
        this.colorProducto = colorProducto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Métodos de utilidad
    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                ", esVentaMayorista=" + esVentaMayorista +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", codigoProducto='" + codigoProducto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedido)) return false;
        DetallePedido that = (DetallePedido) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
