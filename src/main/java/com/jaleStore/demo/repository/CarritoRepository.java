package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.CarritoItem;
import com.jaleStore.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Buscar carritos por ID de usuario y estado activo
    List<Carrito> findByUsuarioIdAndActivo(Long usuarioId, Boolean activo);

    // Buscar carritos por entidad Usuario y estado activo
    List<Carrito> findByUsuarioAndActivo(Usuario usuario, Boolean activo);

    // Query personalizada para obtener carrito activo ordenado por fecha
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = :activo ORDER BY c.fechaActualizacion DESC")
    List<Carrito> findCarritoActivoByUsuario(@Param("usuarioId") Long usuarioId, @Param("activo") Boolean activo);

    // Buscar carrito por ID de usuario (independientemente del estado)
    Optional<Carrito> findByUsuarioId(Long usuarioId);

    // Query personalizada para obtener UN carrito activo por usuario
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = true")
    Optional<Carrito> findCarritoActivoByUsuarioId(@Param("usuarioId") Long usuarioId);

    // CORREGIDO: Este método debe retornar List<Carrito>, no List<CarritoItem>
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.activo = true")
    List<Carrito> findCarritosActivosByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Si necesitas obtener los items de carritos activos, usa este método separado:
    @Query("SELECT ci FROM CarritoItem ci JOIN ci.carrito c WHERE c.usuario.id = :usuarioId AND c.activo = true AND ci.activo = true")
    List<CarritoItem> findCarritoItemsActivosByUsuarioId(@Param("usuarioId") Long usuarioId);
}