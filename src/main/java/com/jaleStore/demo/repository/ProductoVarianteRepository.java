package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.ImagenVariante;
import com.jaleStore.demo.entity.LoteProducto;
import com.jaleStore.demo.entity.Producto;
import com.jaleStore.demo.entity.ProductoVariante;
import com.jaleStore.demo.util.CategoriaZapatilla;
import com.jaleStore.demo.util.ColorZapatilla;
import com.jaleStore.demo.util.GeneroZapatilla;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Repositorio para variantes con consultas específicas
@Repository
public interface ProductoVarianteRepository extends JpaRepository<ProductoVariante, Long> {

    Optional<ProductoVariante> findByProductoAndTallaAndColor(
            Producto producto, Double talla, ColorZapatilla color);

    @Query("SELECT pv FROM ProductoVariante pv WHERE pv.producto.id = :productoId " +
            "AND (:talla IS NULL OR pv.talla = :talla) " +
            "AND (:color IS NULL OR pv.color = :color) " +
            "AND pv.stock > 0 AND pv.activo = true")
    List<ProductoVariante> findVariantesDisponibles(
            @Param("productoId") Long productoId,
            @Param("talla") Double talla,
            @Param("color") ColorZapatilla color);

    @Query("SELECT pv FROM ProductoVariante pv " +
            "WHERE pv.producto.activo = true AND pv.activo = true AND pv.stock > 0 " +
            "AND (:marca IS NULL OR pv.producto.marca ILIKE %:marca%) " +
            "AND (:categoria IS NULL OR pv.producto.categoria = :categoria) " +
            "AND (:genero IS NULL OR pv.producto.genero = :genero) " +
            "AND (:tallaMin IS NULL OR pv.talla >= :tallaMin) " +
            "AND (:tallaMax IS NULL OR pv.talla <= :tallaMax) " +
            "AND (:color IS NULL OR pv.color = :color)")
    Page<ProductoVariante> buscarVariantesConFiltros(
            @Param("marca") String marca,
            @Param("categoria") CategoriaZapatilla categoria,
            @Param("genero") GeneroZapatilla genero,
            @Param("tallaMin") Double tallaMin,
            @Param("tallaMax") Double tallaMax,
            @Param("color") ColorZapatilla color,
            Pageable pageable);

    @Query("SELECT DISTINCT pv.producto FROM ProductoVariante pv " +
            "WHERE pv.stock > 0 AND pv.activo = true AND pv.producto.activo = true")
    Page<Producto> findProductosConStock(Pageable pageable);

    @Query("SELECT SUM(pv.stock) FROM ProductoVariante pv WHERE pv.producto.id = :productoId")
    Integer calcularStockTotalProducto(@Param("productoId") Long productoId);

    @Query("SELECT pv FROM ProductoVariante pv WHERE pv.sku = :sku")
    Optional<ProductoVariante> findBySku(@Param("sku") String sku);
}


