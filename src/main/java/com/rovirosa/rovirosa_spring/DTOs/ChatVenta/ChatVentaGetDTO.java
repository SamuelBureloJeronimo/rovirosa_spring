package com.rovirosa.rovirosa_spring.DTOs.ChatVenta;

import java.time.LocalDateTime;

public interface ChatVentaGetDTO {
    
    Integer getId();
    Integer getVenta_Id();
    String getMensaje();
    LocalDateTime getEnviado();
    String getArchivo();
    
    Integer getUser_Id();
    String getUser_Persona_Nombre();
}
