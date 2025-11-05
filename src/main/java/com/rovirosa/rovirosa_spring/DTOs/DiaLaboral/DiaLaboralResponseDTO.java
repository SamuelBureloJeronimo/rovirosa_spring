package com.rovirosa.rovirosa_spring.DTOs.DiaLaboral;

import java.util.List;
import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralQueryDTO;

public class DiaLaboralResponseDTO {
    
    private Integer id;
    private String diaSemana;
    private List<HorarioLaboralQueryDTO> horarios;

    public DiaLaboralResponseDTO(Integer id, String diaSemana, List<HorarioLaboralQueryDTO> horarios) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horarios = horarios;
    }

    public List<HorarioLaboralQueryDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioLaboralQueryDTO> horarios) {
        this.horarios = horarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}