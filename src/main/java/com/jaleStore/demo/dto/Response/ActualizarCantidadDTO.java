package com.jaleStore.demo.dto.Response;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class ActualizarCantidadDTO {

    @NotNull(message = "La nueva cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer nuevaCantidad;

    // Constructor vacío
    public ActualizarCantidadDTO() {}

    // Constructor con parámetros
    public ActualizarCantidadDTO(Integer nuevaCantidad) {
        this.nuevaCantidad = nuevaCantidad;
    }
}