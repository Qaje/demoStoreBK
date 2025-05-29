package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.NivelCliente;
import com.jaleStore.demo.util.TipoUsuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UsuarioResumeDTO {
    private Long id;
    private String nombreUsuario;
    private String email;
    private String nombreCompleto;
    private String telefono;
    private TipoUsuario tipoUsuario; // CLIENTE, ADMIN, VENDEDOR
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoAcceso;
    private Integer totalPedidos;
    private BigDecimal totalComprado;
    private NivelCliente nivelCliente; // NUEVO, REGULAR, VIP, MAYORISTA
    private Boolean esMayorista;
    private String avatar; // URL de imagen de perfil

    // Constructores, getters y setters
}
