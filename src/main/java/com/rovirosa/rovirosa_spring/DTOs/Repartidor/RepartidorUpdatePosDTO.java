package com.rovirosa.rovirosa_spring.DTOs.Repartidor;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RepartidorUpdatePosDTO {

    @NotNull(message = "La latitud no puede ser nula")
    private BigDecimal latitud;
    @NotNull(message = "La longitud no puede ser nula")
    private BigDecimal longitud;
    
}