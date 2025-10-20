package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import java.time.LocalDateTime;

import com.rovirosa.rovirosa_spring.models.Producto;

public interface CarritoQueryByClienteDTO {
    
    Integer getId();
    Producto getProducto();
    Integer getCantidad();
    LocalDateTime getCreated();
    LocalDateTime getUpdated();

}
