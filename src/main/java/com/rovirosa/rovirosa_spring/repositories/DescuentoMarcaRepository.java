package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoConfigQueryDTO;
import com.rovirosa.rovirosa_spring.models.DescuentoMarca;

public interface DescuentoMarcaRepository extends JpaRepository<DescuentoMarca, Integer> {
    
    DescuentoConfigQueryDTO findByMarca_Id(Integer id);
    
}
