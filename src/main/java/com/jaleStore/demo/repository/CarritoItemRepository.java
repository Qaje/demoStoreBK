package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    List<CarritoItem> findByUsuarioIdAndActivoTrue(Long usuarioId);

    List<CarritoItem> findByUsuarioId(Long usuarioId);

    @Query("SELECT ci FROM CarritoItem ci WHERE ci.usuario.id = :usuarioId AND ci.activo = true")
    List<CarritoItem> findActiveItemsByUsuarioId(@Param("usuarioId") Long usuarioId);

    void deleteByUsuarioIdAndActivoFalse(Long usuarioId);
}