package com.rovirosa.rovirosa_spring.DTOs.DetalleVenta;

import com.rovirosa.rovirosa_spring.models.Producto;

public interface DetalleVentaResponseDTO { 

    Integer getId();
    Producto getProducto();
    Integer getCantIn();
    Integer getCantFin();
    Double getPrecioUnit();
    Double getDescUnit();
    
}