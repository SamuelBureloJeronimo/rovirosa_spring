package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.PuntoDistribucion;

public interface PuntoDistribucionRepository extends JpaRepository<PuntoDistribucion, Integer> {
    
}
