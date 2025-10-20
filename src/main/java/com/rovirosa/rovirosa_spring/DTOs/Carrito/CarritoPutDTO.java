package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import jakarta.validation.constraints.NotNull;

public class CarritoPutDTO {

    @NotNull(message = "El ID del carrito no puede ser nulo")
    private Integer id;

    @NotNull(message = "La cantidad no puede ser nula")
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
