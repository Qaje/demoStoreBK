package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.ImagenVariante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagenVarianteRepository extends JpaRepository<ImagenVariante, Long> {

    List<ImagenVariante> findByVarianteIdOrderByOrden(Long varianteId);

    Optional<ImagenVariante> findByVarianteIdAndEsPrincipalTrue(Long varianteId);

    @Modifying
    @Query("UPDATE ImagenVariante i SET i.esPrincipal = false WHERE i.variante.id = :varianteId")
    void desmarcarImagenesPrincipales(@Param("varianteId") Long varianteId);
}
