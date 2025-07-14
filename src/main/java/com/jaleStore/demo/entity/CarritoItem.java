package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.TipoVenta;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "carrito_items")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // OPCIÓN 1: Relación directa con Producto (RECOMENDADA)
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para evitar LazyInitializationException
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // OPCIÓN 2: Solo el ID del producto (si prefieres esta opción)
    // @Column(name = "producto_id", nullable = false)
    // private Long productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "es_venta_mayorista", nullable = false)
    private Boolean esVentaMayorista = false;

//    @Column(name = "precio_unitario", precision = 10, scale = 2)
//    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "precio_mayor", precision = 10, scale = 2)
    private BigDecimal precioMayor;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    // Constructor vacío
    public CarritoItem() {
        this.fechaAgregado = LocalDateTime.now();
    }

    // Constructor con parámetros
    public CarritoItem(Usuario usuario, Producto producto, Integer cantidad, Boolean esVentaMayorista) {
        this();
        this.usuario = usuario;
        this.producto = producto;
        this.cantidad = cantidad;
        this.esVentaMayorista = esVentaMayorista;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Si usas la opción 2 (solo ID):
    // public Long getProductoId() {
    //     return productoId;
    // }
    //
    // public void setProductoId(Long productoId) {
    //     this.productoId = productoId;
    // }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario(){
        return precioUnitario;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getEsVentaMayorista() {
        return esVentaMayorista;
    }

    public void setEsVentaMayorista(Boolean esVentaMayorista) {
        this.esVentaMayorista = esVentaMayorista;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

//    public void setCarrito(Carrito carrito) {
//    }
//
//    public void setVariante(ProductoVariante variante) {
//    }
//
//    public void setTipoVenta(TipoVenta tipoVenta) {
//    }
//
    public void setPrecioUnitario(BigDecimal precio) {}

    // Agregar estos métodos faltantes a tu clase CarritoItem:

//    public void setPrecioUnitario(BigDecimal precioUnitario) {
//        this.precioUnitario = precioUnitario;
//    }

    public BigDecimal getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(BigDecimal precioMayor) {
        this.precioMayor = precioMayor;
    }
}