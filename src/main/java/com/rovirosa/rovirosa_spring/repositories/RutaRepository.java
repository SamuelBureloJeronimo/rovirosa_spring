package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {

    @Query("SELECT r FROM Ruta r WHERE r.estado IN :estados AND r.puntoVenta.id = :pvId")
    List<Ruta> findRutasActivas(@Param("estados") List<String> estados, @Param("pvId") Integer pvId);

    @Query("SELECT ra.rep FROM RepartidorAsignacion ra WHERE ra.puntoVenta.id = :id AND ra.rep.estado IN :estados")
    List<Repartidor> findRepartidoresActivos(@Param("id") Integer pvId, @Param("estados") List<String> estados);
}
