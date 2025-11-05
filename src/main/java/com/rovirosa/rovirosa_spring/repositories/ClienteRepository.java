package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteQueryDireccionDTO;
import com.rovirosa.rovirosa_spring.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    ClienteQueryDireccionDTO findByUsuario_Id(Integer userId);
    
}