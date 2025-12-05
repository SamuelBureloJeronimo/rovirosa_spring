package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.models.RutaDetalle;

public interface RutaDetalleRepository extends JpaRepository<RutaDetalle, Integer> {

    List<RutaDetalleQueryByRepartidorIdDTO> findByRuta_Repartidor_IdAndRuta_Estado(Integer repId, String estado);

    List<RutaDetalleQueryByRepartidorIdDTO> findByRuta_PuntoVenta_IdAndRuta_EstadoAndRuta_RepartidorIsNull(Integer pvId, String estado);

    RutaDetalleQueryByRepartidorIdDTO findFirstById(Integer id);

    RutaDetalleQueryByRepartidorIdDTO findFirstByVenta_Id(Integer ventaId);

    List<RutaDetalle> findByRutaIsNullAndVenta_PuntoVenta_Id(Integer pvId);
    
}
