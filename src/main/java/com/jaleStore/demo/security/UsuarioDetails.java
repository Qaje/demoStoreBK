package com.jaleStore.demo.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UsuarioDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean activo; // Agregar campo para manejar estado

    public UsuarioDetails(Long id, String email, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.activo = true; // Por defecto activo
    }

    // Constructor completo
    public UsuarioDetails(Long id, String email, String password,
                          Collection<? extends GrantedAuthority> authorities, boolean activo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return activo; // Si está activo, no está bloqueado
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return activo; // El usuario está habilitado si está activo
    }
}
