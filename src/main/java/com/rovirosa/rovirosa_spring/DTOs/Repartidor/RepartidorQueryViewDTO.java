package com.rovirosa.rovirosa_spring.DTOs.Repartidor;

import java.time.LocalDate;

public interface RepartidorQueryViewDTO {
    
    Integer getId();
    LocalDate getFechaIn();
    LocalDate getFechaFin();
    Boolean getActivo();
    
}
