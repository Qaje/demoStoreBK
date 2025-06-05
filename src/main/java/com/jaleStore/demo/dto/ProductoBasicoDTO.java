package com.jaleStore.demo.dto;

import java.math.BigDecimal;

class ProductoBasicoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioUnidad;
    private BigDecimal precioMayor;
    private String imagen;

    // Constructores
    public ProductoBasicoDTO() {}

    public ProductoBasicoDTO(Long id, String nombre, BigDecimal precioUnidad) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnidad = precioUnidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioUnidad() { return precioUnidad; }
    public void setPrecioUnidad(BigDecimal precioUnidad) { this.precioUnidad = precioUnidad; }

    public BigDecimal getPrecioMayor() { return precioMayor; }
    public void setPrecioMayor(BigDecimal precioMayor) { this.precioMayor = precioMayor; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}