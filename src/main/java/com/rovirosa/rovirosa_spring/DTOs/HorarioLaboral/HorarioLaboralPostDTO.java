package com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral;

import jakarta.validation.constraints.NotBlank;

public class HorarioLaboralPostDTO {

    @NotBlank(message = "La hora de apertura no puede ser nula")
    private String hApertura;
    @NotBlank(message = "La hora de cierre no puede ser nula")
    private String hCierre;

    public String gethApertura() {
        return hApertura;
    }
    public void sethApertura(String hApertura) {
        this.hApertura = hApertura;
    }

    public String gethCierre() {
        return hCierre;
    }
    public void sethCierre(String hCierre) {
        this.hCierre = hCierre;
    }
}
