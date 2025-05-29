package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoImagen;

public class ImagenVarianteDTO {
    private Long id;
    private Long varianteId; // ID de la variante (talla + color)
    private String url;
    private String urlThumbnail;
    private String nombreArchivo;
    private String altText;
    private Integer orden; // Para ordenar las imágenes
    private Boolean esPrincipal;
    private TipoImagen tipoImagen; // FRONTAL, LATERAL, TRASERA, DETALLE, SUELA
    private String color; // Color específico de esta imagen
    private Integer ancho;
    private Integer alto;
    private Long tamaño; // En bytes

    // Constructores, getters y setters
}