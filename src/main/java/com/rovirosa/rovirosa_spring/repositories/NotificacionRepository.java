package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Notificaciones.NotificacionDTO;
import com.rovirosa.rovirosa_spring.models.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    List<NotificacionDTO> findByUser_Id(Integer userId);

    @Modifying
    @Query("UPDATE Notificacion n SET n.leido = true WHERE n.id = :id")
    int markAsRead(@Param("id") Integer id);

}
