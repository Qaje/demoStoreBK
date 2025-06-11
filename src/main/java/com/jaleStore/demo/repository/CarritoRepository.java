package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {

    //Busca un carrito por ID de usuario y estado activo}
    List<Carrito> findByUsuarioAndActivo(Long usuarioId, Boolean activo);

    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = :activo ORDER BY c.fechaActualizacion DESC")
    List<Carrito> findCarritoActivoByUsuario(@Param("usuarioId") Long usuarioId, @Param("activo") Boolean activo);


    //Busca un carrito por ID  de usuario(independientemente del estado)
    Optional<Carrito> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = true")
    Optional<Carrito> findCarritoActivoByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = true")
    List<CarritoItem> findByUsuarioIdAndActivoTrue(Long usuarioId);
}
