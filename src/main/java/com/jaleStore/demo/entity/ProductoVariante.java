package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.ColorZapatilla;
import com.jaleStore.demo.util.TipoImagen;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "producto_variantes")
public class ProductoVariante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Double talla;           // 38, 39, 40, 41, 42, etc.

    @Enumerated(EnumType.STRING)
    private ColorZapatilla color;   // ROJO, VERDE, NEGRO, BLANCO, AZUL, etc.

    private String codigoColor;     // Código hexadecimal del color: #FF0000
    private String nombreColor;     // "Rojo Intenso", "Verde Militar", etc.

    private Integer stock;          // Stock específico para esta talla+color
    private String sku;             // Código único: "NIKE-AM270-38-RED"

    // Imágenes específicas para esta variante
    @OneToMany(mappedBy = "variante", cascade = CascadeType.ALL)
    private List<ImagenVariante> imagenes = new ArrayList<>();

    private Boolean activo = true;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private TipoImagen tipo;

    // Método para generar SKU automáticamente
    @PrePersist
    public void generarSku() {
        if (this.sku == null && this.producto != null) {
            String marca = this.producto.getMarca().replaceAll("\\s+", "").toUpperCase();
            String modelo = this.producto.getModelo().replaceAll("\\s+", "").toUpperCase();
            String colorCode = this.color.name().substring(0, 3);
            this.sku = String.format("%s-%s-%.0f-%s", marca, modelo, this.talla, colorCode);
        }
    }


}
