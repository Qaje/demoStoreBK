package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.CategoriaZapatilla;
import com.jaleStore.demo.util.GeneroZapatilla;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;          // Ej: "Nike Air Max 270"
    private String marca;           // Ej: "Nike"
    private String descripcion;     // Descripción general del modelo
    private String modelo;          // Ej: "Air Max 270"
    private String talla;
    private String color;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnidad;      // Precio base unitario

    @Column(precision = 10, scale = 2)
    private BigDecimal precioMayor;     // Precio base mayorista

    private Integer cantidadMayorista = 12; // A partir de qué cantidad es mayorista

    @Enumerated(EnumType.STRING)
    private CategoriaZapatilla categoria;   // DEPORTIVA, CASUAL, RUNNING, etc.

    @Enumerated(EnumType.STRING)
    private GeneroZapatilla genero;         // HOMBRE, MUJER, UNISEX, NIÑO

    private Integer stock;

    private Boolean activo = true;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Relación con variantes (talla + color)
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoVariante> variantes = new ArrayList<>();
}
