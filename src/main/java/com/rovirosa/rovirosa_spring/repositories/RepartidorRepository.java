package com.rovirosa.rovirosa_spring.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorGetVehDTO;
import com.rovirosa.rovirosa_spring.models.Repartidor;

public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {

    Repartidor findByUsuario_Id(Integer usuarioId);

    Repartidor findByUsuario_Persona_Curp(String curp);

    RepartidorGetVehDTO findFirstById(Integer id);

    List<RepartidorGetVehDTO> findByUsuario_PuntoVenta_Id(Integer pvId);

    @Modifying
    @Query("UPDATE Repartidor p SET p.estado = :estado WHERE p.id = :id")
    Integer updateEstadoRepartidor(@Param("id") Integer id, @Param("estado") String estado);

    @Modifying
    @Query("UPDATE Repartidor p SET p.lat = :latitud, p.lng = :longitud WHERE p.id = :id")
    Integer updatePosition(@Param("id") Integer id, @Param("latitud") BigDecimal latitud, @Param("longitud") BigDecimal longitud);

    @Modifying
    @Query("UPDATE Repartidor p SET p.vehiculo.id = :vhId WHERE p.id = :id")
    Integer asignarVehiculo(@Param("id") Integer id, @Param("vhId") Integer vhId);

}
