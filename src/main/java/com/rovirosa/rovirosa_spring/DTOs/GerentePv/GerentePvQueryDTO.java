package com.rovirosa.rovirosa_spring.DTOs.GerentePv;

import com.rovirosa.rovirosa_spring.models.Persona;

public interface GerentePvQueryDTO {
    
    Integer getId();

    Persona getPersona();

    String getCorreo();
}
