package com.jaleStore.demo.security;

import com.jaleStore.demo.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getPassword())
                    .authorities(getAuthorities(usuario))
                    .accountNonExpired(true)
                    .accountNonLocked(usuario.getActivo())
                    .credentialsNonExpired(true)
                    .enabled(usuario.getActivo())
                    .build();
        };
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Agregar rol principal
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));

        // Agregar permisos específicos según el tipo de usuario
        switch (usuario.getRol()) {
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_USERS"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_INVENTORY"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_ORDERS"));
                authorities.add(new SimpleGrantedAuthority("PERM_VIEW_REPORTS"));
                break;
            case VENDEDOR:
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_INVENTORY"));
                authorities.add(new SimpleGrantedAuthority("PERM_MANAGE_ORDERS"));
                break;
            case MAYORISTA:
                authorities.add(new SimpleGrantedAuthority("PERM_WHOLESALE_PRICES"));
                break;
            case CLIENTE:
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
