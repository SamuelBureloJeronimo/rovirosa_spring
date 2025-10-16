package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoConfigQueryDTO;
import com.rovirosa.rovirosa_spring.models.DescuentoCategoria;

public interface DescuentoCategRepository extends JpaRepository<DescuentoCategoria, Integer> {
    
    DescuentoConfigQueryDTO findByCategoria_Id(Integer id);
    
}
