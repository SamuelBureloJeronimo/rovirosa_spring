package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRep;

    
    public Direccion getDireccion(Integer id) {
        return direccionRep.findById(id).orElse(null);
    }

    
    public Direccion updateDireccion(Direccion direccion) {
        return direccionRep.save(direccion);
    }
    
}
