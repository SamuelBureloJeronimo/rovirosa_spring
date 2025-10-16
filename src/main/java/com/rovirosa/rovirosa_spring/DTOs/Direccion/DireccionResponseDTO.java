package com.rovirosa.rovirosa_spring.DTOs.Direccion;

import java.math.BigDecimal;

import com.rovirosa.rovirosa_spring.models.Direccion;

public class DireccionResponseDTO {

    private BigDecimal lat;
    private BigDecimal lng;
    private String ref;

    public DireccionResponseDTO(Direccion dto) {
        this.lat = dto.getLat();
        this.lng = dto.getLng();
        this.ref = dto.getRef();
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public String getRef() {
        return ref;
    }
    
}
