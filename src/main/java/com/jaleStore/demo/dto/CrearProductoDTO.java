package com.jaleStore.demo.dto;

import com.jaleStore.demo.util.CategoriaZapatilla;
import com.jaleStore.demo.util.GeneroZapatilla;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrearProductoDTO {
    private String nombre;
    private String marca;
    private String descripcion;
    private String modelo;
    private BigDecimal precioUnitario;
    private BigDecimal precioMayorista;
    private Integer cantidadMayorista;
    private CategoriaZapatilla categoria;
    private GeneroZapatilla genero;

    // getters y setters
}