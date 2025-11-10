package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaQueryClienteDTO;
import com.rovirosa.rovirosa_spring.models.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    List<VentaQueryClienteDTO> findByCliente_Id(Integer clienteId);
    
}
