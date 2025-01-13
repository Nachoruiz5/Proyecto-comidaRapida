package com.rotiseria.modeladoDeSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rotiseria.modeladoDeSoftware.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
