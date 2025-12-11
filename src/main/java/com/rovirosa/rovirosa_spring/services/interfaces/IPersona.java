package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Persona;

public interface IPersona {
    
    ApiResponse<Persona> getById(Integer id);

    ApiResponse<Void> deletePersona(Integer id);

}
