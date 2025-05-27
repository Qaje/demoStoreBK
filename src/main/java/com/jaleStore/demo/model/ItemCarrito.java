package com.jaleStore.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_carritos")
public class ItemCarrito {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double precioAplicado;
}
