package com.rovirosa.rovirosa_spring.DTOs.GerentePv;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GerenteAsignToPvDTO {
    @NotNull(message = "El ID del gerente no puede ser nulo")
    private Integer gerenteId;
    @NotNull(message = "El ID del punto de venta no puede ser nulo")
    private Integer pvId;
}
