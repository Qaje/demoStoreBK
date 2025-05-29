package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.CategoriaZapatilla;
import com.jaleStore.demo.util.GeneroZapatilla;

import java.math.BigDecimal;
import java.util.List;

// Producto con sus variantes disponibles
public class ProductoConVariantesDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String descripcion;
    private String modelo;
    private BigDecimal precioUnitario;
    private BigDecimal precioMayorista;
    private Integer cantidadMayorista;
    private CategoriaZapatilla categoria;
    private GeneroZapatilla genero;
    private List<ProductoVarianteDTO> variantes;
    private ImagenVarianteDTO imagenPrincipal;
    private Boolean tieneStock;

    // getters y setters
}
