package com.rovirosa.rovirosa_spring.DTOs.Direccion;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class DireccionPostDTO {

    @NotNull(message = "La latitud no puede ser nula")
    private BigDecimal lat;
    @NotNull(message = "La longitud no puede ser nula")
    private BigDecimal lng;

    @NotNull(message = "La referencia no puede ser nula")
    private String ref = "";

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
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }

}