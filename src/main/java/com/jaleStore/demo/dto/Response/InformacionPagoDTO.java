package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.EstadoPago;
import com.jaleStore.demo.util.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InformacionPagoDTO {
    private Long id;
    private String codigoTransaccion;
    private MetodoPago metodoPago; // QR_BOLIVIANO, QR_DOLARES, TRANSFERENCIA
    private BigDecimal monto;
    private String moneda; // BOB, USD
    private String qrCode; // Código QR generado
    private String urlQr; // URL del QR para mostrar
    private EstadoPago estadoPago; // PENDIENTE, PAGADO, EXPIRADO, CANCELADO
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaExpiracion;
    private LocalDateTime fechaPago;
    private String bancoEmisor;
    private String numeroReferencia;
    private String comprobantePago; // URL del comprobante subido
    private String notasPago;
    private BigDecimal comision;

    // Constructores, getters y setters
}
