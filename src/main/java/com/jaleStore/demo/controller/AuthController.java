package com.jaleStore.demo.controller;

import com.jaleStore.demo.dto.LoginRequest;
import com.jaleStore.demo.dto.RefreshRequest;
import com.jaleStore.demo.dto.RegistroRequest;
import com.jaleStore.demo.dto.Response.AuthResponse;
import com.jaleStore.demo.security.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {

            String identifier = request.getUsername() != null ? request.getUsername() : request.getEmail();

            // Autenticar usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            identifier,
                            request.getPassword()
                    )
            );

            // Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(identifier);

            // Generar tokens
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(
                    accessToken,
                    refreshToken,
                    userDetails.getUsername(),
                    userDetails.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .toList()
            ));

        } catch (Exception e) {
            log.error("Error en login: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, "Error de autenticación", null));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        try {
            String username = jwtService.extractUsername(request.getRefreshToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(request.getRefreshToken(), userDetails)) {
                String newAccessToken = jwtService.generateToken(userDetails);

                return ResponseEntity.ok(new AuthResponse(
                        newAccessToken,
                        request.getRefreshToken(),
                        userDetails.getUsername(),
                        userDetails.getAuthorities().stream()
                                .map(authority -> authority.getAuthority())
                                .toList()
                ));
            }

            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            log.error("Error en refresh: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }



//    @Data
//    public static class RefreshRequest {
//        private String refreshToken;
//    }

//    @Data
//    public static class AuthResponse {
//        private String accessToken;
//        private String refreshToken;
//        private String username;
//        private java.util.List<String> roles;
//
//        public AuthResponse(String accessToken, String refreshToken, String username, java.util.List<String> roles) {
//            this.accessToken = accessToken;
//            this.refreshToken = refreshToken;
//            this.username = username;
//            this.roles = roles;
//        }
//    }
}