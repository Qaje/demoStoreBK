package com.jaleStore.demo.dto;

// DTO básico para ProductoVariante
class ProductoVarianteBasicoDTO {
    private Long id;
    private String nombre;
    private String color;
    private String talla;
    private ProductoBasicoDTO producto;

    // Constructores
    public ProductoVarianteBasicoDTO() {}

    public ProductoVarianteBasicoDTO(Long id, String nombre, String color, String talla) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.talla = talla;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public ProductoBasicoDTO getProducto() { return producto; }
    public void setProducto(ProductoBasicoDTO producto) { this.producto = producto; }
}
