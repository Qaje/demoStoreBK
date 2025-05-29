package com.jaleStore.demo.dto.Mapper;

import com.jaleStore.demo.dto.ProductoDTO;
import com.jaleStore.demo.entity.Producto;
import org.springframework.stereotype.Component;


    @Component
    public class ProductoMapper {

        // Convertir de Producto a ProductoDTO
        public static ProductoDTO toDTO(Producto producto) {
            if (producto == null) {
                return null;
            }

            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setMarca(producto.getMarca());
            dto.setDescripcion(producto.getDescripcion());
            dto.setModelo(producto.getModelo());
            dto.setPrecioUnidad(producto.getPrecioUnidad());
            dto.setPrecioMayor(producto.getPrecioMayor());
            dto.setCategoria(String.valueOf(producto.getCategoria()));
            dto.setGenero(String.valueOf(producto.getGenero()));
            dto.setFechaCreacion(producto.getFechaCreacion());
            dto.setFechaActualizacion(producto.getFechaActualizacion());

            return dto;
        }
    }

