package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rovirosa.rovirosa_spring.models.Marca;

public interface CarritoQueryByClienteDTO {
    
    Integer getId();
    Integer getCantidad();
    LocalDateTime getCreated();
    LocalDateTime getUpdated();
    
    Integer getCatalogo_Id();
    Integer getCatalogo_Stock();
    
    Integer getCatalogo_Producto_Id();
    String getCatalogo_Producto_Nombre();
    String getCatalogo_Producto_Imagen();
    Double getCatalogo_Producto_Precio();    
    Double getCatalogo_Producto_PesoKg();    
    BigDecimal getCatalogo_Producto_VolM3();    
    
    Marca getCatalogo_Producto_Marca();

    Integer getCatalogo_PuntoVenta_Id();
    String getCatalogo_PuntoVenta_Nombre();

}
