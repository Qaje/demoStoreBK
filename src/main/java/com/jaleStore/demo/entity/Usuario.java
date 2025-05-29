package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.RolUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol; // ADMIN, CLIENTE

    private Boolean activo = true;
    private LocalDateTime fechaRegistro;
}
