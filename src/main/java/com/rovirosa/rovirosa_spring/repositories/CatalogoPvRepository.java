package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;

public interface CatalogoPvRepository extends JpaRepository<CatalogoPv, Integer> {

    List<CatalogoQueryDTO> findByPuntoVenta_IdAndProducto_Marca_Id(Integer id, Integer marcaId);
    List<CatalogoQueryDTO> findByPuntoVenta_Id(Integer id);

    CatalogoQueryDTO findCatalogoQueryDTOById(Integer id);

    Boolean existsByProducto_IdAndPuntoVenta_Id(Integer productoId, Integer puntoVentaId);
    
    Integer deleteByProducto_IdAndPuntoVenta_Id(Integer productoId, Integer puntoVentaId);
}
