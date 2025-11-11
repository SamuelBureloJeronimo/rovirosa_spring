package com.rovirosa.rovirosa_spring.DTOs.DetalleVenta;

public interface DetalleVentaResponseDTO { 

    Integer getId();
    String getProductoImagen();
    Integer getCantIn();
    Integer getCantFin();
    Double getPrecioUnit();
    Double getDescUnit();

    Double getProducto_PesoKg();
    Double getProducto_VolM3();
    
}