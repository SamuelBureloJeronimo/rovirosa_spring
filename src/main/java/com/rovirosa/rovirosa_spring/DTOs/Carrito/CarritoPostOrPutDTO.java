package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import jakarta.validation.constraints.NotNull;

public class CarritoPostOrPutDTO {

    private int id = 0;
    @NotNull(message = "El ID del cliente no puede ser nulo")
    private int clienteId;
    @NotNull(message = "El ID del punto de venta no puede ser nulo")
    private int pvId;
    @NotNull(message = "El ID del producto no puede ser nulo")
    private int productoId;
    @NotNull(message = "La cantidad no puede ser nula")
    private int cantidad;

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}
