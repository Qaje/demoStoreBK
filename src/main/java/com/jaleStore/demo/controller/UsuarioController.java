package com.jaleStore.demo.controller;

import com.jaleStore.demo.dto.RegistroRequest;
import com.jaleStore.demo.service.UsuarioService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest request) {
        try {
            usuarioService.registrarUsuario(request);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (ConstraintViolationException e) {
            Map<String, String> errores = new HashMap<>();
            e.getConstraintViolations().forEach(violation -> {
                String campo = violation.getPropertyPath().toString();
                String mensaje = violation.getMessage();
                errores.put(campo, mensaje);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }
}
