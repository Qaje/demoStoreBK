package com.jaleStore.demo.dto.Response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResumeDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String modelo;
    private String categoria; // Running, Basketball, Casual, etc.
    private BigDecimal precioUnitario;
    private BigDecimal precioMayoreo;
    private Integer cantidadMinimaMayoreo;
    private Integer stockTotal;
    private Boolean disponible;
    private String imagenPrincipal; // URL de imagen principal
    private Double calificacionPromedio;
    private Integer totalReseñas;
    private Boolean esNovedad;
    private Boolean enOferta;
    private BigDecimal porcentajeDescuento;


}
