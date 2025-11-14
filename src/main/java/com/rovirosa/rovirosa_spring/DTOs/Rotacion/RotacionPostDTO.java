package com.rovirosa.rovirosa_spring.DTOs.Rotacion;

import java.time.LocalDate;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotacionPostDTO {

    @NotNull
    private Integer repartidorId;
    @NotNull
    private Integer puntoVentaId;
    @NotBlank
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
}