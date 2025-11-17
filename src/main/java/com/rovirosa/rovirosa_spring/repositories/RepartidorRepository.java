package com.rovirosa.rovirosa_spring.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.models.Repartidor;

public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {

    Repartidor findByUsuario_Id(Integer usuarioId);

    Repartidor findByUsuario_Persona_Curp(String curp);

    @Modifying
    @Query("UPDATE Repartidor p SET p.lat = :latitud, p.lng = :longitud WHERE p.id = :id")
    Integer updatePosition(@Param("id") Integer id, @Param("latitud") BigDecimal latitud, @Param("longitud") BigDecimal longitud);

    @Modifying
    @Query("UPDATE Repartidor p SET p.vehiculo.id = :vhId WHERE p.id = :id")
    Integer asignarVehiculo(@Param("id") Integer id, @Param("vhId") Integer vhId);

}
