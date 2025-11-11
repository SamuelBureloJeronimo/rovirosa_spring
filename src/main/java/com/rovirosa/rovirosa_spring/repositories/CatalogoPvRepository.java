package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;

public interface CatalogoPvRepository extends JpaRepository<CatalogoPv, Integer> {

    List<CatalogoQueryDTO> findByPuntoVenta_IdAndProducto_Marca_Id(Integer id, Integer marcaId);

    List<CatalogoQueryDTO> findByPuntoVenta_Id(Integer id);

    CatalogoQueryDTO findCatalogoQueryDTOById(Integer id);

    CatalogoQueryDTO findFirstCatalogoQueryDTOByProducto_IdAndPuntoVenta_Id(Integer prod_Id, Integer pvId);

    @Modifying
    @Query("UPDATE CatalogoPv p SET p.stock = :stock WHERE p.puntoVenta.id = :puntoVentaId AND p.producto.id = :productoId")
    int updateStock(@Param("puntoVentaId") Integer puntoVentaId, @Param("productoId") Integer productoId, @Param("stock") Integer stock);

    Boolean existsByProducto_IdAndPuntoVenta_Id(Integer productoId, Integer puntoVentaId);
    
    Integer deleteByProducto_IdAndPuntoVenta_Id(Integer productoId, Integer puntoVentaId);
}
