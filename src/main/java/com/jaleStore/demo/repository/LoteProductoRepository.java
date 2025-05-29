package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.LoteProducto;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoteProductoRepository extends JpaRepository<LoteProducto, Long> {

    Long countByFechaIngresoAfter(LocalDateTime fecha);

    @Query("SELECT l FROM LoteProducto l WHERE l.producto.id = :productoId ORDER BY l.fechaIngreso DESC")
    List<LoteProducto> findByProductoIdOrderByFechaDesc(@Param("productoId") Long productoId);

    @Query("SELECT l FROM LoteProducto l WHERE " +
            "(:fechaDesde IS NULL OR l.fechaIngreso >= :fechaDesde) AND " +
            "(:fechaHasta IS NULL OR l.fechaIngreso <= :fechaHasta) " +
            "ORDER BY l.fechaIngreso DESC")
    Page<LoteProducto> findLotesConFiltros(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta,
            Pageable pageable);
}
