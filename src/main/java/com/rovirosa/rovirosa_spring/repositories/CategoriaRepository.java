package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    
}
