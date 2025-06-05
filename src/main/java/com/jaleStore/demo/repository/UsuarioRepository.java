package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);


    boolean existsByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.activo = true")
    boolean findActivo(@Param("nombre")String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> findByRol(@Param("rol")String rol);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.activo = true")
    Optional<Usuario> findByEmailAndActivoTrue(@Param("email")String email);

    //buscar usuarios por nombre para busquedas
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre% OR u.apellido LIKE %:nombre%")
    Optional<Usuario> findByNombreContaining(@Param("nombre")String nombre);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    Long countUsuariosActivos();

    @Query("SELECT u FROM Usuario u WHERE u.activo = :activo")
    java.util.List<Usuario> findByActivo(@Param("activo") Boolean activo);
}
