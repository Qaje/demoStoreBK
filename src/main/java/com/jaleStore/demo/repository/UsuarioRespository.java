package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRespository extends JpaRepository<Usuario,Long> {
}
