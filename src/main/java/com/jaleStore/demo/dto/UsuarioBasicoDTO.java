package com.jaleStore.demo.dto;


// DTO básico para Usuario (sin información sensible)
public class UsuarioBasicoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    // Constructores
    public UsuarioBasicoDTO() {
    }

    public UsuarioBasicoDTO(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

