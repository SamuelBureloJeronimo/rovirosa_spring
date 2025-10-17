package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaSimpleQueryDTO;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;

public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Integer> {
    @Modifying
    @Query("UPDATE PuntoVenta p SET p.zonaPermitida = :zona WHERE p.id = :id")
    int updateZona(@Param("id") Integer id, @Param("zona") String zona);

    @Query("SELECT p.direccion FROM PuntoVenta p WHERE p.id = :id")
    Direccion findDireccionByPuntoVentaId(@Param("id") Integer id);

    List<PuntoVentaQueryDTO> findAllProjectedBy();

    PuntoVentaQueryDTO findProjectedById(Integer id);

    PuntoVentaSimpleQueryDTO findSimpleProjectedById(Integer id);
    
}
