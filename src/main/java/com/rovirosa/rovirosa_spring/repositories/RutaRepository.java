package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.models.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    
}
