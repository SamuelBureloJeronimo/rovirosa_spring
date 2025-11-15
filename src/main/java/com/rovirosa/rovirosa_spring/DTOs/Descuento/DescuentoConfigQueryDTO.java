package com.rovirosa.rovirosa_spring.DTOs.Descuento;

import java.time.LocalDate;

public interface DescuentoConfigQueryDTO {

    public String getConfig_Tipo();
    public Double getConfig_Valor();
    public LocalDate getConfig_FechaIn();
    public LocalDate getConfig_FechaFin();

}
