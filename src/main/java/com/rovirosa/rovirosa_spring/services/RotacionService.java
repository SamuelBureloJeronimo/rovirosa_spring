package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignacionQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RotacionPostDTO;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.RepartidorAsignacion;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorAsignacionRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;

import jakarta.transaction.Transactional;

@Service
public class RotacionService {

    @Autowired
    private RepartidorAsignacionRepository repAsignRep;
    @Autowired
    private RepartidorRepository repartidorRep;
    @Autowired
    private PuntoVentaRepository puntoVentaRep;

    @Transactional
    public void asignToPv(RotacionPostDTO dto) {

        System.out.println(dto.getPuntoVentaId());
        System.out.println(dto.getRepartidorId());
        System.out.println(dto.getFechaInicio());
        System.out.println(dto.getFechaFin());

        RepartidorAsignacion repAsign = new RepartidorAsignacion();

        // Cargar referencias REALES
        Repartidor repa = repartidorRep.getReferenceById(dto.getRepartidorId());
        PuntoVenta pv = puntoVentaRep.getReferenceById(dto.getPuntoVentaId());

        repa.getUsuario().setPuntoVenta(pv);

        repAsign.setRep(repa);
        repAsign.setPuntoVenta(pv);

        repAsign.setFechaIn(dto.getFechaInicio());
        repAsign.setFechaFin(dto.getFechaFin());

        repartidorRep.save(repa);
        repAsignRep.save(repAsign);
    }

    public List<RepartidorAsignacionQueryDTO> getRotacion() {
        return repAsignRep.findAllBy();
    }

    public List<RepartidorAsignacionQueryDTO> getRotacionByPvId(Integer pvId) {
        return repAsignRep.findAllByPuntoVenta_Id(pvId);
    }

    public void eliminarRotacion(Integer id) {
        repAsignRep.deleteById(id);
    }

}
