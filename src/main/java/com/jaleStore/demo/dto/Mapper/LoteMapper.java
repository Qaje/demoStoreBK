package com.jaleStore.demo.dto.Mapper;

import com.jaleStore.demo.dto.DetalleLoteDTO;
import com.jaleStore.demo.dto.Response.LoteProductoDTO;
import com.jaleStore.demo.entity.LoteProducto;
import com.jaleStore.demo.entity.DetalleLote;
import com.jaleStore.demo.entity.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class LoteMapper {

    public static LoteProductoDTO toDTO(LoteProducto lote) {
        if (lote == null) {
            return null;
        }

        LoteProductoDTO dto = new LoteProductoDTO();
        dto.setId(lote.getId());
        dto.setNumeroLote(lote.getNumeroLote());
        dto.setDescripcion(lote.getDescripcion());
        dto.setTotalPares(lote.getTotalPares());
        dto.setTallaMinima(lote.getTallaMinima());
        dto.setTallaMaxima(lote.getTallaMaxima());
        dto.setCostoTotal(lote.getCostoTotal());
        dto.setCostoPorPar(lote.getCostoPorPar());
        dto.setProveedor(lote.getProveedor());
        dto.setFechaIngreso(lote.getFechaIngreso());

        // Mapear producto si existe
        if (lote.getProducto() != null) {
            List<Producto> lstProductos= null;
            Producto producto = lote.getProducto();
            producto.setId(lote.getProducto().getId());
            producto.setNombre(lote.getProducto().getNombre());
            producto.setMarca(lote.getProducto().getMarca());
        }

//        // Mapear detalles si existen
//        if (lote.getDetalles() != null && !lote.getDetalles().isEmpty()) {
//            dto.setDetalles(lote.getDetalles().stream()
//                    .map(LoteMapper::mapearDetalle)
//                    .collect(Collectors.toList()));
//        }

        return dto;
    }

    private static DetalleLoteDTO mapearDetalle(DetalleLote detalle) {
        DetalleLoteDTO dto = new DetalleLoteDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());

        if (detalle.getVariante() != null) {
            dto.setVarianteId(detalle.getVariante().getId());
            dto.setTalla(Double.valueOf(detalle.getVariante().getTalla()));
            dto.setColor(detalle.getVariante().getColor());
            dto.setNombreColor(detalle.getVariante().getNombreColor());
            dto.setCodigoColor(detalle.getVariante().getCodigoColor());
        }

        return dto;
    }
}
