package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.models.GerentePv;

public interface GerentePvRepository extends JpaRepository<GerentePv, Integer> {

    GerentePvQueryDTO findFirstByPuntoVentaId(Integer id);
    
}
