package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;

public interface CatalogoPvRepository extends JpaRepository<CatalogoPv, Integer> {

    List<CatalogoQueryDTO> findByPuntoVenta_IdAndProducto_Marca_Id(Integer id, Integer marcaId);
}
