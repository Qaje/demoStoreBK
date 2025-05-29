package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.EstadoPedido;

import java.time.LocalDateTime;

public class HistorialEstadoDTO {
    private Long id;
    private Long pedidoId;
    private EstadoPedido estadoAnterior;
    private EstadoPedido estadoNuevo;
    private String comentario;
    private String usuarioResponsable; // Quien cambió el estado
    private LocalDateTime fechaCambio;
    private String ubicacion; // Para seguimiento de envío
    private String transportista; // Si aplica
    private String numeroGuia; // Para envíos
    private Boolean notificacionEnviada;

    // Constructores, getters y setters
}