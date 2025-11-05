package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralQueryDTO;
import com.rovirosa.rovirosa_spring.repositories.DiaLaboralRepository;
import com.rovirosa.rovirosa_spring.repositories.HorarioLaboralRepository;

@Service
public class DiaLaboralService {
    
    @Autowired
    private DiaLaboralRepository diaLaboralRepository;
    @Autowired
    private HorarioLaboralRepository horarioLaboralRepository;

    public List<DiaLaboralResponseDTO> getDiasLaboralesByPuntoVentaId(Integer puntoVentaId) {
        List<DiaLaboralQueryDTO> diaLaboral = diaLaboralRepository.findByPuntoVenta_Id(puntoVentaId);

        List<DiaLaboralResponseDTO> res = new ArrayList<>();

        for (DiaLaboralQueryDTO dia : diaLaboral) {
            List<HorarioLaboralQueryDTO> horarios = horarioLaboralRepository.findByDiaLaboral_Id(dia.getId());
            DiaLaboralResponseDTO diaResponse = new DiaLaboralResponseDTO(dia.getId(), dia.getDiaSemana(), horarios);
            res.add(diaResponse);
        }
        return res;
    }

}
