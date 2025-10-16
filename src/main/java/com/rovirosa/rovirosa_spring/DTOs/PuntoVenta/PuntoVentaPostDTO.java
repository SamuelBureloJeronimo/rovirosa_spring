package com.rovirosa.rovirosa_spring.DTOs.PuntoVenta;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PuntoVentaPostDTO {

    @NotBlank(message = "El RFC no puede estar vacío")
    private String rfc;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La latitud no puede estar vacía")
    private BigDecimal lat;

    @NotNull(message = "La longitud no puede estar vacía")
    private BigDecimal lng;

    @NotBlank(message = "La zona no puede estar vacía")
    private String zona;


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
    public BigDecimal getLat() {
        return lat;
    }
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
    public BigDecimal getLng() {
        return lng;
    }
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
    public String getZona() {
        return zona;
    }
    public void setZona(String zona) {
        this.zona = zona;
    }

    
    
}
