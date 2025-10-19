package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaDetallesDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaSimpleQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryDTO;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.repositories.CatalogoPvRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.GerentePvRepository;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorAsignacionRepository;

@Service
public class PuntoVentaService {

    @Autowired
    private PuntoVentaRepository puntoRep;
    @Autowired
    private DireccionRepository dirRep;
    @Autowired
    private RepartidorAsignacionRepository repAsignRep;
    @Autowired
    private GerentePvRepository gerenteRep;
    @Autowired
    private CatalogoPvRepository catalogoRep;

    public PuntoVentaDetallesDTO getPuntoDetalles(Integer id) {

        PuntoVentaSimpleQueryDTO punto = puntoRep.findSimpleProjectedById(id);
        List<RepartidorQueryDTO> repartidores = repAsignRep.findByPuntoVentaId(id);
        GerentePvQueryDTO gerente = gerenteRep.findFirstByPuntoVentaId(id);
        List<CatalogoQueryDTO> catalogo = catalogoRep.findByPuntoVenta_Id(id);

        PuntoVentaDetallesDTO detalles = new PuntoVentaDetallesDTO(punto, repartidores, gerente, catalogo);
        return detalles;
    }

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

    @Transactional
    public boolean deletePunto(Integer id) {
        if (puntoRep.existsById(id)) {
            puntoRep.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
