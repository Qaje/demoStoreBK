package com.jaleStore.demo.repository;

import com.jaleStore.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRespository extends JpaRepository<Producto, Long> {
}
