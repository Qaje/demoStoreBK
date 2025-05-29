package com.jaleStore.demo.dto.Response;

import com.jaleStore.demo.util.TipoImagen;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImagenDTO {
    private Long id;
    private Long productoId;
    private String url;
    private String urlThumbnail;
    private String nombreArchivo;
    private String tipoMime;
    private Long tamaño;
    private Integer ancho;
    private Integer alto;
    private String altText;
    private String descripcion;
    private Integer orden;
    private Boolean esPrincipal;
    private TipoImagen tipoImagen;
    private String color; // Si la imagen es específica de un color
    private Boolean activa;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    // Constructores, getters y setters
}
