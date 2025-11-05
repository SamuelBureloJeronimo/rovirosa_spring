package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.models.DiaLaboral;

public interface DiaLaboralRepository extends JpaRepository<DiaLaboral, Integer> {

    List<DiaLaboralQueryDTO> findByPuntoVenta_Id(Integer puntoVentaId);

} 
