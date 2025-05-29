package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.TipoVenta;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_carritos")
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "variante_id")  // Cambiado a variante específica
    private ProductoVariante variante;

    private Integer cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnitario; // Precio al momento de agregar

    @Enumerated(EnumType.STRING)
    private TipoVenta tipoVenta; // UNITARIA, MAYORISTA

    // Método para calcular subtotal
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
