package com.rovirosa.rovirosa_spring.DTOs.Rotacion;

import java.time.LocalDate;

public interface RepartidorAsignSimpleDTO {
    Integer getId();
    Integer getRep_Id();
    Integer getRep_Vehiculo_Id();
    LocalDate getFechaIn();
    LocalDate getFechaFin();
}
