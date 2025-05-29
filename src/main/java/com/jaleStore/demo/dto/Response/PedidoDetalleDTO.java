package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDetalleDTO {
    private Long id;
    private String numeroPedido;
    private UsuarioResumeDTO cliente;
    private List<ItemPedidoDetalleDTO> items;
    private BigDecimal subtotal;
    private BigDecimal descuentos;
    private BigDecimal total;
    private EstadoPedido estado;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaPago;
    private DatosEntregaDTO datosEntrega;
    private InformacionPagoDTO informacionPago;
    private List<HistorialEstadoDTO> historialEstados;

    // getters y setters
}
