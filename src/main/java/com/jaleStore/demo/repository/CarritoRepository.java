package com.jaleStore.demo.repository;

import com.jaleStore.demo.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {
}
