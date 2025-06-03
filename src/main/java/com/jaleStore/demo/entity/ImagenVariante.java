package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.TipoImagen;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "imagen_variantes")
public class ImagenVariante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variante_id")
    private ProductoVariante variante;

    private String url;             // URL de la imagen
    private String alt;             // Texto alternativo
    private Integer orden;          // Orden de visualización (1 = principal)
    private Boolean esPrincipal;    // Si es la imagen principal de la variante

    @Enumerated(EnumType.STRING)
    private TipoImagen tipo;        // FRONTAL, LATERAL, TRASERA, SUELA, DETALLE
}
