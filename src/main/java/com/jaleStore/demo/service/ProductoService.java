package com.jaleStore.demo.service;

import com.jaleStore.demo.dto.CrearLoteDTO;
import com.jaleStore.demo.dto.CrearProductoDTO;
import com.jaleStore.demo.dto.Mapper.ProductoMapper;
import com.jaleStore.demo.dto.ProductoDTO;
import com.jaleStore.demo.dto.Response.ImagenDTO;
import com.jaleStore.demo.dto.Response.LoteProductoDTO;
import com.jaleStore.demo.dto.Response.ProductoVarianteDTO;
import com.jaleStore.demo.dto.VarianteLoteDTO;
import com.jaleStore.demo.entity.*;
import com.jaleStore.demo.repository.LoteProductoRepository;
import com.jaleStore.demo.repository.ProductoRepository;
import com.jaleStore.demo.repository.ProductoVarianteRepository;
import com.jaleStore.demo.util.ColorZapatilla;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVarianteRepository varianteRepository;

    @Autowired
    private LoteProductoRepository loteRepository;

    // Crear producto base
    public ProductoDTO crearProductoBase(CrearProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setMarca(dto.getMarca());
        producto.setDescripcion(dto.getDescripcion());
        producto.setModelo(dto.getModelo());
        producto.setPrecioUnidad(dto.getPrecioUnitario());
        producto.setPrecioMayor(dto.getPrecioMayorista());
        producto.setCategoria(dto.getCategoria());
        producto.setGenero(dto.getGenero());

        return ProductoMapper.toDTO(productoRepository.save(producto));
    }

    // Crear lote masivo con variantes
    @Transactional
    public LoteProductoDTO crearLoteMasivo(CrearLoteDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Crear lote
        LoteProducto lote = new LoteProducto();
        lote.setNumeroLote(generarNumeroLote());
        lote.setDescripcion(dto.getDescripcion());
        lote.setProducto(producto);
        lote.setTotalPares(dto.getVariantes().stream()
                .mapToInt(VarianteLoteDTO::getCantidad).sum());
        lote.setTallaMinima(dto.getTallaMinima());
        lote.setTallaMaxima(dto.getTallaMaxima());
        lote.setCostoTotal(dto.getCostoTotal());
        lote.setCostoPorPar(dto.getCostoTotal().divide(
                BigDecimal.valueOf(lote.getTotalPares()), 2, RoundingMode.HALF_UP));
        lote.setProveedor(dto.getProveedor());
        lote.setFechaIngreso(LocalDateTime.now());

        lote = loteRepository.save(lote);

        // Crear/actualizar variantes
        for (VarianteLoteDTO varianteDto : dto.getVariantes()) {
            ProductoVariante variante = varianteRepository
                    .findByProductoAndTallaAndColor(producto, varianteDto.getTalla(), varianteDto.getColor())
                    .orElse(new ProductoVariante());

            if (variante.getId() == null) {
                // Nueva variante
                variante.setProducto(producto);
                variante.setTalla(varianteDto.getTalla());
                variante.setColor(varianteDto.getColor());
                variante.setNombreColor(varianteDto.getColor().getNombre());
                variante.setCodigoColor(varianteDto.getColor().getCodigoHex());
                variante.setStock(varianteDto.getCantidad());
                variante.setFechaCreacion(LocalDateTime.now());
            } else {
                // Actualizar stock existente
                variante.setStock(variante.getStock() + varianteDto.getCantidad());
            }

            variante = varianteRepository.save(variante);

            // Crear detalle del lote
            DetalleLote detalle = new DetalleLote();
            detalle.setLote(lote);
            detalle.setVariante(variante);
            detalle.setCantidad(varianteDto.getCantidad());
            lote.getDetalles().add(detalle);

            // Procesar imágenes si las hay
            if (varianteDto.getImagenes() != null && !varianteDto.getImagenes().isEmpty()) {
                procesarImagenesVariante(variante, varianteDto.getImagenes());
            }
        }

        return LoteMapper.toDTO(lote);
    }

    // Buscar variantes disponibles
    public List<ProductoVarianteDTO> buscarVariantesDisponibles(
            Long productoId, Double talla, ColorZapatilla color) {

        List<ProductoVariante> variantes = varianteRepository
                .findVariantesDisponibles(productoId, talla, color);

        return variantes.stream()
                .map(VarianteMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Verificar stock de variante
    public boolean verificarStock(Long varianteId, Integer cantidad) {
        ProductoVariante variante = varianteRepository.findById(varianteId)
                .orElseThrow(() -> new EntityNotFoundException("Variante no encontrada"));

        return variante.getStock() >= cantidad;
    }

    // Reducir stock (usado al confirmar pedido)
    @Transactional
    public void reducirStock(Long varianteId, Integer cantidad) {
        ProductoVariante variante = varianteRepository.findById(varianteId)
                .orElseThrow(() -> new EntityNotFoundException("Variante no encontrada"));

        if (variante.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Disponible: " + variante.getStock() +
                            ", Solicitado: " + cantidad);
        }

        variante.setStock(variante.getStock() - cantidad);
        varianteRepository.save(variante);
    }

    private String generarNumeroLote() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long contador = loteRepository.countByFechaIngresoAfter(
                LocalDateTime.now().toLocalDate().atStartOfDay());
        return String.format("LOTE-%s-%03d", fecha, contador + 1);
    }

    private void procesarImagenesVariante(ProductoVariante variante, List<ImagenDTO> imagenes) {
        for (ImagenDTO imagenDto : imagenes) {
            ImagenVariante imagen = new ImagenVariante();
            imagen.setVariante(variante);
            imagen.setUrl(imagenDto.getUrl());
            imagen.setAlt(imagenDto.getAlt());
            imagen.setOrden(imagenDto.getOrden());
            imagen.setEsPrincipal(imagenDto.getEsPrincipal());
            imagen.setTipo(imagenDto.getTipo());

            variante.getImagenes().add(imagen);
        }
    }
}
