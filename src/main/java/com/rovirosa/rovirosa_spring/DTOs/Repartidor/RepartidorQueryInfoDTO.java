package com.rovirosa.rovirosa_spring.DTOs.Repartidor;

import java.time.LocalDate;

import com.rovirosa.rovirosa_spring.models.Persona;

public interface RepartidorQueryInfoDTO {
    
    Integer getId();
    String getEstado();

    Persona getUsuario_Persona();
    Integer getVehiculo_Id();

    LocalDate getFechaFin();
    Boolean getActivo();

}
