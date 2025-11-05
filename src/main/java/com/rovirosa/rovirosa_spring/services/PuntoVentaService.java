package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaDetallesDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaSimpleQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryViewDTO;
import com.rovirosa.rovirosa_spring.models.DiaLaboral;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.HorarioLaboral;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.repositories.CatalogoPvRepository;
import com.rovirosa.rovirosa_spring.repositories.DiaLaboralRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.GerentePvRepository;
import com.rovirosa.rovirosa_spring.repositories.HorarioLaboralRepository;
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
    @Autowired
    private HorarioLaboralRepository horarioRep;
    @Autowired
    private DiaLaboralRepository diaLaboralRep;

    public PuntoVentaDetallesDTO getPuntoDetalles(Integer id) {

        PuntoVentaSimpleQueryDTO punto = puntoRep.findSimpleProjectedById(id);
        List<RepartidorQueryViewDTO> repartidores = repAsignRep.findByPuntoVentaId(id);
        GerentePvQueryDTO gerente = gerenteRep.findFirstByPuntoVentaId(id);
        List<CatalogoQueryDTO> catalogo = catalogoRep.findByPuntoVenta_Id(id);

        List<DiaLaboralQueryDTO> diasQuery = diaLaboralRep.findByPuntoVenta_Id(id);

        List<DiaLaboralResponseDTO> diasResponse = new ArrayList<>();

        for (DiaLaboralQueryDTO dia : diasQuery) {
            List<HorarioLaboralQueryDTO> horariosDia = horarioRep.findByDiaLaboral_Id(dia.getId());
            diasResponse.add(new DiaLaboralResponseDTO(dia.getId(), dia.getDiaSemana(), horariosDia));
        }

        PuntoVentaDetallesDTO detalles = new PuntoVentaDetallesDTO(punto, repartidores, gerente, catalogo, diasResponse);
        return detalles;
    }

    @Transactional
    public PuntoVentaResponseDTO createPunto(PuntoVentaPostDTO dto, List<DiaLaboralPostDTO> horarios) {

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

        for (DiaLaboralPostDTO diaDTO : horarios) {
            DiaLaboral dia = new DiaLaboral();
            dia.setPuntoVenta(punto);
            dia.setDiaSemana(diaDTO.getDiaSemana());

            dia = diaLaboralRep.save(dia);

            for (HorarioLaboralPostDTO horarioDTO : diaDTO.getHorarios()) {
                HorarioLaboral hl = new HorarioLaboral();
                hl.setDiaLaboral(dia);
                hl.sethApertura(horarioDTO.gethApertura().toString());
                hl.sethCierre(horarioDTO.gethCierre().toString());
                hl.setDiaLaboral(dia);
                horarioRep.save(hl);

            }
        }

        return new PuntoVentaResponseDTO(punto);

    }

    @Transactional(readOnly = true)
    public List<PuntoVentaQueryDTO> getAllPuntos() {
        return puntoRep.findAllProjectedBy();
    }

    public PuntoVentaQueryDTO getDireccion(Integer id) {
        return puntoRep.findProjectedById(id);
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
