package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.TipoVenta;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_pedidos")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "variante_id")  // Cambiado a variante específica
    private ProductoVariante variante;

    private Integer cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    private TipoVenta tipoVenta;

    // Datos adicionales para el historial
    private String nombreProducto;   // Guardamos el nombre por si se elimina
    private String marcaProducto;
    private Double talla;
    private String color;
    private String skuVariante;
}
