package com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpresaConfigUpdateDTO {

    @NotNull(message = "El RFC no puede ser nulo")
    private String rfc;
    
    private String nombre;
    private String logo;
    private String descrip;
    private Integer montoMin;
    private Integer comision;

}
