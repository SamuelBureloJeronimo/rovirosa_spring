package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.ChatVenta.ChatVentaGetDTO;
import com.rovirosa.rovirosa_spring.models.ChatVenta;

public interface ChatVentaRepository extends JpaRepository<ChatVenta, Integer> {
    
    List<ChatVentaGetDTO> findByVenta_Id(Integer ventaId);
    
    ChatVentaGetDTO findFirstById(Integer id);

}
