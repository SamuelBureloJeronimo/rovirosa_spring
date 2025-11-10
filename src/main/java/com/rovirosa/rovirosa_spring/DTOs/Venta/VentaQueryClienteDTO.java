package com.rovirosa.rovirosa_spring.DTOs.Venta;

import com.rovirosa.rovirosa_spring.models.Pago;

public interface VentaQueryClienteDTO {

    Integer getId();
    String getFechaInicio();
    String getFechaFin();
    String getEstado();
    Integer getCalif();
    Pago getPago();
    
}
