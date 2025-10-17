package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryDTO;
import com.rovirosa.rovirosa_spring.models.RepartidorAsignacion;

public interface RepartidorAsignacionRepository extends JpaRepository<RepartidorAsignacion, Integer> {

    List<RepartidorQueryDTO> findByPuntoVentaId(Integer puntoVentaId);
    
}
