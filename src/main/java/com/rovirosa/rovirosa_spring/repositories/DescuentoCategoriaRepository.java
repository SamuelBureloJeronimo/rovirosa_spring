package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.models.DescuentoCategoria;

public interface DescuentoCategoriaRepository extends JpaRepository<DescuentoCategoria, Integer> {
    
}
