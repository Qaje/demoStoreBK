package com.jaleStore.demo.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Autowired
//    private JwtService jwtService;

//    @Autowired
//    private UserDetailsService userDetailsService;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verificar si el header Authorization existe y tiene el formato correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT
        jwt = authHeader.substring(7);

        try {
            // Extraer el email del usuario del token
            userEmail = jwtService.extractUsername(jwt);

            // Si el usuario existe y no está autenticado aún
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Cargar detalles del usuario
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validar el token
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // Crear token de autenticación
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Establecer detalles adicionales
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Establecer autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.debug("Usuario autenticado: {}", userEmail);
                }
            }
        } catch (JwtException e) {
            log.error("Error de JWT: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token JWT inválido\"}");
            return;
        } catch (Exception e) {
            log.error("Error en autenticación: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Excluir rutas públicas del filtro JWT
        return path.startsWith("/api/auth/") ||
                path.startsWith("/api/public/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/") ||
                path.startsWith("/api/productos/publicos") ||
                path.startsWith("/api/categorias/publicas");
    }
}
