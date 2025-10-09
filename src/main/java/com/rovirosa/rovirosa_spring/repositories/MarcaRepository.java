package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.models.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{

    List<Marca> findByCategoriaId(Integer categoriaId);
    
}
