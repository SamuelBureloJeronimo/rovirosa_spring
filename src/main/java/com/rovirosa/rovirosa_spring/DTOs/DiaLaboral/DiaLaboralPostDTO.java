package com.rovirosa.rovirosa_spring.DTOs.DiaLaboral;
import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralPostDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DiaLaboralPostDTO {

    @NotBlank(message = "El día de la semana no puede estar vacío")
    private String diaSemana;
    @NotNull(message = "Los horarios no pueden ser nulos")
    private List<HorarioLaboralPostDTO> horarios;

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<HorarioLaboralPostDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioLaboralPostDTO> horarios) {
        this.horarios = horarios;
    }
    
}
