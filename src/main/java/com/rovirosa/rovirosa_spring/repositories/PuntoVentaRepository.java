package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.PuntoVenta;

public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Integer> {
    @Modifying
    @Query("UPDATE PuntoVenta p SET p.zonaPermitida = :zona WHERE p.id = :id")
    int updateZona(@Param("id") Integer id, @Param("zona") String zona);
}
