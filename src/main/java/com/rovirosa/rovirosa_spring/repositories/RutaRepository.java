package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaQueryDTO;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {

    @Query("""
                SELECT
                    r.id AS id,
                    r.fechaIn AS fechaIn,
                    r.fechaFin AS fechaFin,
                    r.estado AS estado,
                    rep.id AS repartidor_Id,
                    p.nombre AS repartidor_Nombre,
                    p.app AS repartidor_App,
                    p.apm AS repartidor_Apm
                FROM Ruta r
                LEFT JOIN r.repartidor rep
                LEFT JOIN rep.usuario u
                LEFT JOIN u.persona p
                WHERE r.estado IN :estados
                  AND r.puntoVenta.id = :pvId
            """)
    List<RutaQueryDTO> findRutasActivas(
            @Param("estados") List<String> estados,
            @Param("pvId") Integer pvId);

    @Modifying
    @Query("UPDATE Ruta r SET r.estado = :estado WHERE r.id = :id")
    Integer updateEstadoRuta(@Param("id") Integer id, @Param("estado") String estado);

    @Query("SELECT ra.rep FROM RepartidorAsignacion ra WHERE ra.puntoVenta.id = :id AND ra.rep.estado IN :estados")
    List<Repartidor> findRepartidoresActivos(@Param("id") Integer pvId, @Param("estados") List<String> estados);
}
