package com.jaleStore.demo.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String nombreUsuario;
    private String email;
    private String password;
    private String apellido;
    private String nombre;

    // Getters y Setters
}