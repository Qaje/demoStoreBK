package com.jaleStore.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class DetalleLote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private LoteProducto lote;

    @ManyToOne
    @JoinColumn(name = "variante_id")
    private ProductoVariante variante;

    private Integer cantidad; // Cantidad de esta variante en el lote
}
