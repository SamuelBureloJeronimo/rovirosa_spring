package com.rovirosa.rovirosa_spring.DTOs.Rotacion;

import java.time.LocalDate;

import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.Vehiculo;

public interface RepartidorAsignacionQueryDTO {
    
    Integer getId();
    String getPuntoVenta_Nombre();
    Integer getRep_Id();
    Integer getRep_Usuario_Id();
    Persona getRep_Usuario_Persona();
    Vehiculo getRep_Vehiculo();
    LocalDate getFechaIn();
    LocalDate getFechaFin();
    
}
