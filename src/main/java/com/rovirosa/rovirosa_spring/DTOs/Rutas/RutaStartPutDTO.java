package com.rovirosa.rovirosa_spring.DTOs.Rutas;

import java.util.List;
import com.google.firebase.database.annotations.NotNull;

import lombok.Data;

@Data
public class RutaStartPutDTO {

    @NotNull("rutaId no debe ser nulo")
    Integer rutaId;
    @NotNull("repId no debe ser nulo")
    Integer repId;
    @NotNull("ventasId no debe ser nulo")
    List<Integer> ventasId;
    
}
