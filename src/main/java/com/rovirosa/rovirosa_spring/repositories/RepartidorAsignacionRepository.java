package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryViewDTO;
import com.rovirosa.rovirosa_spring.models.RepartidorAsignacion;

public interface RepartidorAsignacionRepository extends JpaRepository<RepartidorAsignacion, Integer> {

    List<RepartidorQueryViewDTO> findByPuntoVentaId(Integer puntoVentaId);
    
}
