package com.jaleStore.demo.dto;

import com.jaleStore.demo.dto.Response.ImagenDTO;
import com.jaleStore.demo.util.ColorZapatilla;
import lombok.Data;

import java.util.List;

@Data
public class VarianteLoteDTO {
    private Double talla;
    private ColorZapatilla color;
    private Integer cantidad;
    private List<ImagenDTO> imagenes;

    // getters y setters
}