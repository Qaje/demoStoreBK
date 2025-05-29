package com.jaleStore.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CrearLoteDTO {
    private Long productoId;
    private String descripcion;
    private Double tallaMinima;
    private Double tallaMaxima;
    private BigDecimal costoTotal;
    private String proveedor;
    private String observaciones;
    private List<VarianteLoteDTO> variantes;

    // getters y setters
}