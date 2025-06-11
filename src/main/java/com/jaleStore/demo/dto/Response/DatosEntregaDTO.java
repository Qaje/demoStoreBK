package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoEntrega;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatosEntregaDTO {
    private Long id;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
    private String pais;
    private String referencias; // Para ubicar mejor la dirección
    private TipoEntrega tipoEntrega; // DOMICILIO, RECOJO_TIENDA
    private LocalDateTime fechaEntregaPreferida;
    private String horarioPreferido;
    private String notasEspeciales;

    // Constructores, getters y setters
}
