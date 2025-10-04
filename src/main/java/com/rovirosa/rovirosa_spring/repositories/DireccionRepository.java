package com.rovirosa.rovirosa_spring.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    @Modifying
    @Query("UPDATE Direccion d SET d.latitud = :latitud, d.longitud = :longitud WHERE d.id = :id")
    int updateCoordenadas(@Param("id") Integer id, @Param("latitud") BigDecimal latitud, @Param("longitud") BigDecimal longitud);
    
}
