package com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig;

import lombok.Data;

@Data
public class EmpresaConfigPutDTO {

    private String rfc;
    private String nombre;
    private String logo;
    private String descrip;
    private Integer montoMin;
    private Integer comision;

}
