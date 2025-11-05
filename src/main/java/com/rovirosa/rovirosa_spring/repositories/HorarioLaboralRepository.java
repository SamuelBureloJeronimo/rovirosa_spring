package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.models.HorarioLaboral;

public interface HorarioLaboralRepository extends JpaRepository<HorarioLaboral, Integer> {

    List<HorarioLaboralQueryDTO> findByDiaLaboral_Id(Integer diaLaboralId);

}
