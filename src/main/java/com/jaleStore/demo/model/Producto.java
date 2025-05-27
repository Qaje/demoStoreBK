package com.jaleStore.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nombre;
    private String marca;
    private Double precioUnidad;
    private Double precioMayor;
    private int stock;
    private String talla;
    private String color;
}
