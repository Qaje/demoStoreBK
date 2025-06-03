package com.jaleStore.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SeguridadUtil {

    public static UsuarioDetails obtenerUsuarioAutenticado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioDetails) {
            return (UsuarioDetails) authentication.getPrincipal();
        } else {
            throw new RuntimeException("No se pudo obtener el usuario autenticado.");
        }
    }
}
