package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Pedido con QR
public class PedidoConQRDTO {
    private Long id;
    private String numeroPedido;
    private BigDecimal total;
    private EstadoPedido estado;
    private LocalDateTime fechaPedido;
    private String urlQR;
    private String instruccionesPago;
    private List<ItemPedidoDTO> items;
    private DatosEntregaDTO datosEntrega;

    // getters y setters
}
