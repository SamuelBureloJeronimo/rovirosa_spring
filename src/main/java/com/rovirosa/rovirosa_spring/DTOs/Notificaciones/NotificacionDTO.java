package com.rovirosa.rovirosa_spring.DTOs.Notificaciones;

import java.time.LocalDateTime;

public interface NotificacionDTO {
    
    Integer getId();
    String getTitulo();
    String getSubtitulo();
    LocalDateTime getFecha();
    Boolean getLeido();

}
