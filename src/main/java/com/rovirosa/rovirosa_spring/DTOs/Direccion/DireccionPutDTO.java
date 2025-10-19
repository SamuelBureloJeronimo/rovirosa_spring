package com.rovirosa.rovirosa_spring.DTOs.Direccion;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;

public class DireccionPutDTO {

    @NotNull(message = "El ID no puede ser nulo")
    private Integer id;
    @NotNull(message = "La latitud no puede ser nula")
    private BigDecimal latitud;
    @NotNull(message = "La longitud no puede ser nula")
    private BigDecimal longitud;
    
    private String ref = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    
}
