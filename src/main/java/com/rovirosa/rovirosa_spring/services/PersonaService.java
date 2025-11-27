package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IPersona;

@Service
public class PersonaService implements IPersona {
    
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public ApiResponse<Persona> getById(Integer id) {
        Persona persona = personaRepository.findById(id).orElse(null);
        if (persona != null) {
            return new ApiResponse<>(true, "Persona found", persona);
        } else {
            return new ApiResponse<>(false, "Persona not found", null);
        }
    }

}
