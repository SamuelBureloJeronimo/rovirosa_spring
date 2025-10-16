package com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig;

public class EmpresaConfigPutDTO {

    private String rfc;
    private String nombre;
    private String logo;
    private String descrip;
    private Integer montoMin;

    // Getters y Setters

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Integer getMontoMin() {
        return montoMin;
    }

    public void setMontoMin(Integer montoMin) {
        this.montoMin = montoMin;
    }

}
