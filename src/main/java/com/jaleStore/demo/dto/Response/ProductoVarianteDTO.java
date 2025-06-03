package com.jaleStore.demo.dto.Response;


import com.jaleStore.demo.util.ColorZapatilla;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductoVarianteDTO {
    private Long id;
    private Double talla;
    private ColorZapatilla color;
    private String nombreColor;
    private String codigoColor;
    private Integer stock;
    private String sku;
    private List<ImagenVarianteDTO> imagenes;
    private BigDecimal precioActual; // Calculado según tipo de venta
    private BigDecimal precioUnidad;
    private BigDecimal precioMayor;
    private Boolean disponible;
    private LocalDateTime fechaCreacion;
    private String productoMarca;


    // getters y setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Double getTalla(){return talla;}
    public void setTalla(Double id){this.talla = talla;}

    public ColorZapatilla getColor(){return color;}
    public void setColor(ColorZapatilla color){this.color = color;}

    public String getNombreColor(){return nombreColor;}
    public void setNombreColor(String nombreColor){this.nombreColor = nombreColor;}

    public String getCodigoColor(){return codigoColor;}
    public void setCodigoColor(String codigoColor){this.codigoColor = codigoColor;}

    public Integer getStock(){return stock;}
    public void setStock(Integer stock){this.stock = stock;}

    public String getSku(){return sku;}
    public void setSku(String sku){this.sku = sku;}

    public List<ImagenVarianteDTO> getImagenes(){return imagenes;}
    public void setImagenes(List<ImagenVarianteDTO> imagenes){this.imagenes = imagenes;}

    public BigDecimal getPrecioActual(){return precioActual;}
    public void setPrecioActual(BigDecimal precioActual){this.precioActual = precioActual;}

    public BigDecimal getPrecioUnidad(){return precioUnidad;}
    public void setPrecioUnidad(BigDecimal precioUnidad){this.precioUnidad = precioUnidad;}

    public BigDecimal getPrecioMayor(){return precioMayor;}
    public void setPrecioMayor(BigDecimal precioMayor){this.precioMayor = precioMayor;}


    public Boolean getDisponible(){return disponible;}
    public void setDisponible(Boolean disponible){this.disponible = disponible;}

    public LocalDateTime getFechaCreacion(){return fechaCreacion;}
    public void setFechaCreacion(LocalDateTime fechaCreacion){this.fechaCreacion = fechaCreacion;}

    public String getProductoMarca(){return productoMarca;}
    public void setProductoMarca(String productoMarca){this.productoMarca = productoMarca;}

}
