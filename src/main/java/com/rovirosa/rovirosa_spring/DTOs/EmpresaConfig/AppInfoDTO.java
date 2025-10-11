package com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig;

public class AppInfoDTO {
    private String nombre;
    private String logo;

    public AppInfoDTO(String nombre, String logo) {
        this.nombre = nombre;
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLogo() {
        return logo;
    }
}
