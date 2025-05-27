package com.jaleStore.demo.DTO;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private String codigo;
    private String marca;
    private Double precioUnidad;
    private Double precioMayor;
    private Integer stock;
    private String talla;
    private String color;
}
