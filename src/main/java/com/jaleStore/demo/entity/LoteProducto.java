package com.jaleStore.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "lote_productos")
public class LoteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroLote;      // Identificador único del lote
    private String descripcion;     // "Lote Nike Air Max 270 - 12 pares"

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer totalPares;     // Total de zapatillas en el lote
    private Double tallaMinima;     // 38
    private Double tallaMaxima;     // 42

    @Column(precision = 10, scale = 2)
    private BigDecimal costoTotal;  // Costo total del lote

    @Column(precision = 10, scale = 2)
    private BigDecimal costoPorPar; // Costo por par individual

    // Detalle de variantes incluidas en el lote
    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<DetalleLote> detalles = new ArrayList<>();

    private LocalDateTime fechaIngreso;
    private String proveedor;
    private String observaciones;
}
