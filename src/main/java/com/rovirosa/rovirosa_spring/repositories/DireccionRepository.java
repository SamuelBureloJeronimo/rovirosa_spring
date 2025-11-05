package com.rovirosa.rovirosa_spring.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    @Modifying
    @Query("UPDATE Direccion d SET d.lat = :lat, d.lng = :lng, d.ref = :ref WHERE d.id = :id")
    int updateCoordenadas(@Param("id") Integer id, @Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng, @Param("ref") String ref);
    
}
