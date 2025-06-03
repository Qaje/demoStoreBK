package com.jaleStore.demo.dto.Mapper;

import com.jaleStore.demo.dto.Response.ImagenDTO;
import com.jaleStore.demo.dto.Response.ProductoVarianteDTO;
import com.jaleStore.demo.entity.ImagenVariante;
import com.jaleStore.demo.entity.ProductoVariante;

import java.util.stream.Collectors;

public class VarianteMapper {
    public static ProductoVarianteDTO toDTO(ProductoVariante variante) {
        if (variante == null) {
            return null;
        }

        ProductoVarianteDTO dto = new ProductoVarianteDTO();
        dto.setId(variante.getId());
        dto.setTalla(variante.getTalla());
        dto.setColor(variante.getColor());
        dto.setNombreColor(variante.getNombreColor());
        dto.setCodigoColor(variante.getCodigoColor());
        dto.setStock(variante.getStock());
        dto.setFechaCreacion(variante.getFechaCreacion());

        // Mapear producto si existe
        if (variante.getProducto() != null) {
//            dto.setProductoId(variante.getProducto().getId());
            dto.setId(variante.getProducto().getId());
//            dto.setProductoNombre(variante.getProducto().getNombre());
            dto.setProductoMarca(variante.getProducto().getMarca());
            dto.setPrecioUnidad(variante.getProducto().getPrecioUnidad());
            dto.setPrecioMayor(variante.getProducto().getPrecioMayor());
        }

//        // Mapear imágenes si existen
//        if (variante.getImagenes() != null && !variante.getImagenes().isEmpty()) {
//            dto.setImagenes(variante.getImagenes().stream()
//                    .map(VarianteMapper::mapearImagen)
//                    .collect(Collectors.toList()));
//        }

        return dto;
    }

    private static ImagenDTO mapearImagen(ImagenVariante imagen) {
        ImagenDTO dto = new ImagenDTO();
        dto.setId(imagen.getId());
        dto.setUrl(imagen.getUrl());
        dto.setAltText(imagen.getAlt());
        dto.setOrden(imagen.getOrden());
        dto.setEsPrincipal(imagen.getEsPrincipal());
        dto.setTipoImagen(imagen.getTipo());
        return dto;
    }

    // Método para mapear de DTO a Entity si es necesario
    public static ProductoVariante toEntity(ProductoVarianteDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductoVariante variante = new ProductoVariante();
        variante.setId(dto.getId());
        variante.setTalla(dto.getTalla());
        variante.setColor(dto.getColor());
        variante.setNombreColor(dto.getNombreColor());
        variante.setCodigoColor(dto.getCodigoColor());
        variante.setStock(dto.getStock());
        variante.setFechaCreacion(dto.getFechaCreacion());

        return variante;
    }
}
