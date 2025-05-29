package com.jaleStore.demo.dto;

import com.jaleStore.demo.util.GeneroZapatilla;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String marca;
    private String descripcion;
    private String modelo;
    private BigDecimal precioUnidad;
    private BigDecimal precioMayor;
    private Integer stock;
    private String talla;
    private String color;
    private String categoria;
    private String genero;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
