package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaResponseDTO;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;

@Service
public class PuntoVentaService {

    @Autowired
    private PuntoVentaRepository puntoRep;
    @Autowired
    private DireccionRepository dirRep;

    @Transactional
    public PuntoVentaResponseDTO createPunto(PuntoVentaPostDTO dto) {

        Direccion direccion = new Direccion();
        direccion.setLat(dto.getLat());
        direccion.setLng(dto.getLng());
        direccion.setRef("");

        direccion = dirRep.save(direccion);

        EmpresaConfig emp = new EmpresaConfig();
        emp.setRfc(dto.getRfc());

        PuntoVenta punto = new PuntoVenta();
        punto.setNombre(dto.getNombre());
        punto.setZonaPermitida(dto.getZona());
        punto.setDireccion(direccion);
        punto.setConfig(emp);

        punto = puntoRep.save(punto);

        return new PuntoVentaResponseDTO(punto);
    }

    @Transactional(readOnly = true)
    public List<PuntoVentaQueryDTO> getAllPuntos() {
        return puntoRep.findAllProjectedBy();
    }

    public DireccionResponseDTO getDireccion(Integer id) {
        return new DireccionResponseDTO(puntoRep.findDireccionByPuntoVentaId(id));
    }

    @Transactional
    public Boolean updateZone(Integer id, String zone) {
        return puntoRep.updateZona(id, zone) > 0;
    }
}
