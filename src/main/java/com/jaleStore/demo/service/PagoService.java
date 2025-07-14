package com.jaleStore.demo.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.jaleStore.demo.entity.Pedido;
import com.jaleStore.demo.repository.PedidoRepository;
import com.jaleStore.demo.util.EstadoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

@Service
public class PagoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Value("${app.base-url}")
    private String baseUrl;

//    @Value("${mercadopago.access-token}")
//    private String mercadoPagoToken;

    /**
     * Genera código QR para pago del pedido
     * @param pedidoId ID del pedido
     * @return Base64 string de la imagen QR o URL de pago
     */
    public String generarQRPago(Long pedidoId) {
        try {
            // 1. Obtener datos del pedido
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

            // 2. Crear URL/datos de pago según la pasarela
            String datosQR = crearDatosPago(pedido);

            // 3. Generar código QR
            return generarCodigoQR(datosQR);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar QR de pago: " + e.getMessage());
        }
    }

    /**
     * Crea los datos de pago según la pasarela elegida
     */
    private String crearDatosPago(Pedido pedido) {
        // OPCIÓN 1: URL simple de pago
        return crearUrlPagoSimple(pedido);

        // OPCIÓN 2: Integración con MercadoPago (descomenta para usar)
        // return crearPagoMercadoPago(pedido);

        // OPCIÓN 3: Datos bancarios/transferencia (descomenta para usar)
        // return crearDatosTransferencia(pedido);
    }

    /**
     * OPCIÓN 1: URL simple de pago
     */
    private String crearUrlPagoSimple(Pedido pedido) {
        return String.format("%s/pagar?pedido=%d&monto=%.2f&token=%s",
                baseUrl,
                pedido.getId(),
                pedido.getTotal(),
                generarTokenSeguro(pedido));
    }

    /**
     * OPCIÓN 2: Integración con MercadoPago
     */
    private String crearPagoMercadoPago(Pedido pedido) {
        try {
            // Configurar preferencia de pago
            Map<String, Object> preference = new HashMap<>();

            // Datos del item
            Map<String, Object> item = new HashMap<>();
            item.put("title", "Pedido #" + pedido.getId());
            item.put("quantity", 1);
            item.put("unit_price", pedido.getTotal());
            preference.put("items", new Object[]{item});

            // URLs de retorno
            Map<String, String> backUrls = new HashMap<>();
            backUrls.put("success", baseUrl + "/pago/exitoso");
            backUrls.put("failure", baseUrl + "/pago/fallido");
            backUrls.put("pending", baseUrl + "/pago/pendiente");
            preference.put("back_urls", backUrls);

            // Referencia externa
            preference.put("external_reference", pedido.getId().toString());

            // Aquí harías la llamada HTTP a MercadoPago API
            // String response = llamarAPICrearPreferencia(preference);
            // return extraerUrlPago(response);

            // Por ahora retornamos URL de ejemplo
            return "http://localhost:8084/checkout/v1/redirect?pref_id=PREF_ID_EJEMPLO";

        } catch (Exception e) {
            throw new RuntimeException("Error al crear pago MercadoPago: " + e.getMessage());
        }
    }

    /**
     * OPCIÓN 3: Datos para transferencia bancaria
     */
    private String crearDatosTransferencia(Pedido pedido) {
        StringBuilder datos = new StringBuilder();
        datos.append("DATOS DE TRANSFERENCIA\n");
        datos.append("Banco: Banco Ejemplo\n");
        datos.append("Cuenta: 123456789\n");
        datos.append("CBU: 1234567890123456789012\n");
        datos.append("Titular: Mi Empresa SRL\n");
        datos.append("Monto: $").append(String.format("%.2f", pedido.getTotal())).append("\n");
        datos.append("Concepto: Pedido #").append(pedido.getId()).append("\n");
        datos.append("Vencimiento: ").append(calcularFechaVencimiento());

        return datos.toString();
    }

    /**
     * Genera el código QR como imagen en Base64
     */
    private String generarCodigoQR(String datos) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(datos, BarcodeFormat.QR_CODE, 300, 300);

            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar código QR: " + e.getMessage());
        }
    }

    /**
     * Genera token de seguridad para el pago
     */
    private String generarTokenSeguro(Pedido pedido) {
        String data = pedido.getId() + ":" + pedido.getTotal() + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(data.getBytes()).substring(0, 16);
    }

    /**
     * Calcula fecha de vencimiento (7 días desde hoy)
     */
    private String calcularFechaVencimiento() {
        java.time.LocalDate fecha = java.time.LocalDate.now().plusDays(7);
        return fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Método auxiliar para validar pago completado
     */
    public boolean validarPago(Long pedidoId, String token) {
        try {
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

            String tokenEsperado = generarTokenSeguro(pedido);

            if (token.equals(tokenEsperado)) {
                pedido.setEstado(EstadoPedido.PAGADO);
                pedidoRepository.save(pedido);
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
