package com.jaleStore.demo.security;

import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            // Usar tu clase UsuarioDetails personalizada
            return new UsuarioDetails(
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getPassword(),
                    getAuthorities(usuario)
            );
        };
    }

    private List<GrantedAuthority> getAuthorities(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Agregar rol principal
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

        // Agregar permisos específicos según el tipo de usuario
        switch (usuario.getRol()) {
            case "ADMIN":
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_USERS"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_INVENTORY"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_ORDERS"));
                authorities.add(new SimpleGrantedAuthority("PERM_VIEW_REPORTS"));
                break;
            case "VENDEDOR":
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_INVENTORY"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_ORDERS"));
                break;
            case "MAYORISTA":
                authorities.add(new SimpleGrantedAuthority("PERM_WHOLESALE_PRICES"));
                break;
            case "CLIENTE":
                authorities.add(new SimpleGrantedAuthority("PERM_PLACE_ORDERS"));
                break;
        }

        return authorities;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
