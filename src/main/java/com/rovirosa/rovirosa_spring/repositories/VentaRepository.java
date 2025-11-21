package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaQueryClienteDTO;
import com.rovirosa.rovirosa_spring.models.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    List<VentaQueryClienteDTO> findByCliente_Id(Integer clienteId);

    List<Venta> findByPuntoVenta_IdAndEstado(Integer id, String estado);

    @Query("""
                SELECT u.tokenFmc
                FROM Cliente c
                JOIN c.usuario u
                WHERE c.id = :id
            """)
    Optional<String> findTokenFmcByClienteId(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Venta v SET v.estado = :estado WHERE v.id = :id")
    Integer updateEstadoVenta(@Param("id") Integer id, @Param("estado") String estado);

    @Modifying
    @Query("UPDATE Venta v SET v.estado = 'Entregado', v.fechaFin = :fechaFin WHERE v.id = :id")
    Integer updateEstadoVentaToEntregado(@Param("id") Integer id, @Param("fechaFin") String fechaFin);

}
