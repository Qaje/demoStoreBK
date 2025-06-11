package com.jaleStore.demo.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jaleStore.demo.entity.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRService {

    @Value("${app.upload.path:/uploads/qr/}")
    private String uploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

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

    public String guardarImagenQR(BitMatrix bitMatrix, String numeroPedido) {
        try {
            // Crear directorio si no existe
            crearDirectorioSiNoExiste();

            // Generar nombre único para la imagen
            String nombreArchivo = generarNombreArchivo(numeroPedido);
            String rutaCompleta = uploadPath + nombreArchivo;

            // Convertir BitMatrix a BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Guardar imagen en el servidor
            File archivoQR = new File(rutaCompleta);
            ImageIO.write(bufferedImage, "PNG", archivoQR);

            // Retornar URL pública de la imagen
            return baseUrl + "/uploads/qr/" + nombreArchivo;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen QR: " + e.getMessage(), e);
        }
    }

    private void crearDirectorioSiNoExiste() {
        try {
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear directorio: " + e.getMessage(), e);
        }
    }

    /**
     * Generar nombre único para el archivo
     */
    private String generarNombreArchivo(String numeroPedido) {
        long timestamp = System.currentTimeMillis();
        return String.format("qr_pedido_%s_%d.png", numeroPedido, timestamp);
    }
}
