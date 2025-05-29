package com.jaleStore.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoService {

    public PedidoDTO crearPedidoDesdeCarrito(Long usuarioId, DatosEntregaDTO datosEntrega) {
        // Crear pedido desde carrito
        // Generar número de pedido único
        // Reducir stock de productos
        // Limpiar carrito
        // Generar QR de pago
    }

    public String generarQRPago(Long pedidoId) {
        // Generar código QR con datos del pedido
        // Integrar con pasarela de pagos
    }

    public void confirmarPago(String transaccionId, Long pedidoId) {
        // Actualizar estado del pedido a PAGADO
        // Enviar confirmación al cliente
    }
}