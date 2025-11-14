package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.models.RutaDetalle;

public interface RutaDetalleRepository extends JpaRepository<RutaDetalle, Integer> {

    List<RutaDetalleQueryByRepartidorIdDTO> findByRuta_Repartidor_Id(Integer repId);
    
}
