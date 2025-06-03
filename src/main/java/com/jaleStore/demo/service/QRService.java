package com.jaleStore.demo.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jaleStore.demo.entity.Pedido;
import org.springframework.stereotype.Service;

@Service
public class QRService {

    public String generarQRPago(Pedido pedido) {
        try {
            // Crear datos del QR (puede ser URL de pago o datos estructurados)
            String datosQR = crearDatosPago(pedido);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(datosQR, BarcodeFormat.QR_CODE, 300, 300);

            // Convertir a imagen y guardar/devolver URL
            return guardarImagenQR(bitMatrix, pedido.getNumeroPedido());

        } catch (Exception e) {
            throw new RuntimeException("Error generando QR", e);
        }
    }

    private String crearDatosPago(Pedido pedido) {
        // Opción 1: URL de pago directo
        return "https://mi-tienda.com/pago/" + pedido.getNumeroPedido();

        // Opción 2: Datos estructurados para Yape/Plin
        // return "yape://pago?monto=" + pedido.getTotal() + "&concepto=" + pedido.getNumeroPedido();
    }
}
