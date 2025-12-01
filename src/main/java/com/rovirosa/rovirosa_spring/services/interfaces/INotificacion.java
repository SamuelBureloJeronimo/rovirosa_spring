package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Notificaciones.NotificacionDTO;

public interface INotificacion {
    
    ApiResponse<Void> create(Integer userId, String titulo, String mensaje);

    ApiResponse<List<NotificacionDTO>> getNotificaciones(Integer userId);

    ApiResponse<Void> leerNotificacion(Integer id);

}
