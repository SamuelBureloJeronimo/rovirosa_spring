package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
}
