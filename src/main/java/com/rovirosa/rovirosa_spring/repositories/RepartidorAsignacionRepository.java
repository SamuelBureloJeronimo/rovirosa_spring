package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryViewDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignSimpleDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignacionQueryDTO;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.RepartidorAsignacion;

public interface RepartidorAsignacionRepository extends JpaRepository<RepartidorAsignacion, Integer> {

    List<RepartidorQueryViewDTO> findByPuntoVentaId(Integer puntoVentaId);

    List<RepartidorAsignacionQueryDTO> findAllBy();

    RepartidorAsignSimpleDTO findFirstByRep_Id(Integer repId);

    Boolean existsByRep_Id(Integer repId);

    List<RepartidorAsignacionQueryDTO> findAllByPuntoVenta_Id(Integer pvId);

    @Query("SELECT ra.rep FROM RepartidorAsignacion ra WHERE ra.puntoVenta.id = :id AND ra.rep.estado IN :estados")
    List<Repartidor> findRepartidoresActivos(@Param("id") Integer pvId, @Param("estados") List<String> estados);
}
