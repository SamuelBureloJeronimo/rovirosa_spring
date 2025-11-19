package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaQueryDTO;
import com.rovirosa.rovirosa_spring.repositories.RutaRepository;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRep;

    public List<RutaQueryDTO> getAllRutasByPvId(Integer pvId) {
        return rutaRep.findRutasActivas(List.of("pendiente", "en_progreso"), pvId);
    }
    
}
