package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoConfigQueryDTO;
import com.rovirosa.rovirosa_spring.models.DescuentoProducto;

public interface DescuentoProductoRepository extends JpaRepository<DescuentoProducto, Integer> {
    
    DescuentoConfigQueryDTO findByProducto_Id(Integer id);
    
}
