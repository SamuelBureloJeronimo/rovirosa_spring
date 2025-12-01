package com.rovirosa.rovirosa_spring.DTOs.Repartidor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RepartidorUpdateStatusDTO {

    @NotNull(message = "El id de repartidor no puede ser nulo")
    private Integer repId;
    private String estado;
    
}
