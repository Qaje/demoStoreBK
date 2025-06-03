package com.jaleStore.demo.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LoteProductoDTO {
    private Long id;
    private String numeroLote;
    private String descripcion;
    private ProductoResumeDTO producto;
    private Integer totalPares;
    private Double tallaMinima;
    private Double tallaMaxima;
    private BigDecimal costoTotal;
    private BigDecimal costoPorPar;
    private LocalDateTime fechaIngreso;
    private String proveedor;
    private List<DetalleLoteDTO> detalles;

    // getters y setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getNumeroLote(){return numeroLote;}
    public void setNumeroLote(String numeroLote){this.numeroLote = numeroLote;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    public ProductoResumeDTO getProducto(){return producto;}
    public void setProducto(ProductoResumeDTO producto){ this.producto = producto; }

    public Integer getTotalPares(){return totalPares;}
    public void setTotalPares(Integer totalPares){this.totalPares = totalPares;}

    public Double getTallaMinima(){return tallaMinima;}
    public void setTallaMinima(Double tallaMinima){this.tallaMinima = tallaMinima;}

    public Double getTallaMaxima(){return tallaMaxima;}
    public void setTallaMaxima(Double tallaMaxima){this.tallaMaxima = tallaMaxima;}

    public BigDecimal getCostoTotal(){return costoTotal;}
    public void setCostoTotal(BigDecimal costoTotal){this.costoTotal = costoTotal;}

    public BigDecimal getCostoPorPar(){return costoPorPar;}
    public void setCostoPorPar(BigDecimal costoPorPar){this.costoPorPar = costoPorPar;}

    public LocalDateTime getFechaIngreso(){return fechaIngreso;}
    public void setFechaIngreso(LocalDateTime fechaIngreso){this.fechaIngreso = fechaIngreso;}

    public String getProveedor(){return proveedor;}
    public void setProveedor(String proveedor){this.proveedor = proveedor;}

    public List<DetalleLoteDTO> getDetalles(){return detalles;}
    public void setDetalles(List<DetalleLoteDTO> detalles){this.detalles = detalles;}



}
