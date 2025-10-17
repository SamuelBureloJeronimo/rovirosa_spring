package com.rovirosa.rovirosa_spring.DTOs.Repartidor;

import java.time.LocalDate;

public interface RepartidorQueryDTO {
    Integer getId();

    LocalDate getFechaIn();
    LocalDate getFechaFin();
    Boolean getActivo();
}
