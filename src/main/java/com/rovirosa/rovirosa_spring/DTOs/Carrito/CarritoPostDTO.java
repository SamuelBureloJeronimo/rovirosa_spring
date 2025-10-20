package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import jakarta.validation.constraints.NotNull;

public class CarritoPostDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private int userId;
    @NotNull(message = "El ID del catálogo no puede ser nulo")
    private int catalogoId;
    @NotNull(message = "La cantidad no puede ser nula")
    private int cantidad;

    public int getUserId() {
        return userId;
    }

    public int getCatalogoId() {
        return catalogoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}
