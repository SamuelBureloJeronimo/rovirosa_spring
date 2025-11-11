package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;
import com.rovirosa.rovirosa_spring.models.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    
    List<DetalleVentaResponseDTO> findByVenta_Id(Integer ventaId);

}
