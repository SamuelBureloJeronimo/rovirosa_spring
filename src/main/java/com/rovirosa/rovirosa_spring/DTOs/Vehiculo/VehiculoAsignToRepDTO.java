package com.rovirosa.rovirosa_spring.DTOs.Vehiculo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehiculoAsignToRepDTO {

    @NotNull(message = "El ID del repartidor no puede ser nulo")
    private Integer repartidorId;
    
    private Integer vehiculoId;
    
}
