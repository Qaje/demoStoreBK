package com.jaleStore.demo.dto.Response;


import com.jaleStore.demo.util.ColorZapatilla;

import java.math.BigDecimal;
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
    private Boolean disponible;

    // getters y setters
}
