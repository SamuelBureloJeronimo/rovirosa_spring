package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
}
