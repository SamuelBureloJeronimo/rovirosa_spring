package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;

@Service
public class PuntoVentaService {

    @Autowired
    private PuntoVentaRepository puntoRep;

    public PuntoVenta createPunto(PuntoVenta punto) {
        return puntoRep.save(punto);
    }
    
    public List<PuntoVenta> getPuntos() {
        return puntoRep.findAll();
    }

    public PuntoVenta updatePunto(PuntoVenta punto) {
        return puntoRep.save(punto);
    }

    public PuntoVenta getPunto(Integer id) {
        return puntoRep.findById(id).orElse(null);
    }

    public void deletePunto(Integer id) {
        puntoRep.deleteById(id);
    }
    
    @Transactional
    public Boolean updateZone(Integer id, String zone) {
        return puntoRep.updateZona(id, zone) > 0;
    }
}
