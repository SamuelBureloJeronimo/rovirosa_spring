package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.models.EmpresaConfig;

public interface EmpresaConfigRepository extends JpaRepository<EmpresaConfig, String> {

    EmpresaConfig findByRfc(String rfc);
    
}
