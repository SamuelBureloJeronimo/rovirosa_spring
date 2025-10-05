package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.CatalogoPv;

public interface CatalogoPvRepository extends JpaRepository<CatalogoPv, Integer> {
    // Aquí puedes definir métodos personalizados de consulta si es necesario
}
