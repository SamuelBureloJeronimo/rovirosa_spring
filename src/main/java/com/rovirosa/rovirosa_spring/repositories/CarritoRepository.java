package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.models.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    
    List<CarritoQueryByClienteDTO> findByCliente_Usuario_Id(Integer id);

}
