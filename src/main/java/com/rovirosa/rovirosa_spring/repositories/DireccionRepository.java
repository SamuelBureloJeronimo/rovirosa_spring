package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    
}
