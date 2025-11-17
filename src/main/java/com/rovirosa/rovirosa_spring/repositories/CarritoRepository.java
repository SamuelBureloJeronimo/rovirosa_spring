package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.models.Carrito;

import jakarta.transaction.Transactional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    
    List<CarritoQueryByClienteDTO> findByUsuario_Id(Integer id);

    CarritoQueryByClienteDTO findProjectedById(Integer id);

    void deleteByUsuario_Id(Integer usuarioId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.catalogo.id = :catalogoId")
    int deleteByUsuarioIdAndCatalogoId(@Param("usuarioId") Integer usuarioId, @Param("catalogoId") Integer catalogoId);

}
