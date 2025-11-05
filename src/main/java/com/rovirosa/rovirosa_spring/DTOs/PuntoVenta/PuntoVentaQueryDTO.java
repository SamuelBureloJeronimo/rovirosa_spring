package com.rovirosa.rovirosa_spring.DTOs.PuntoVenta;

import com.rovirosa.rovirosa_spring.models.Direccion;

public interface PuntoVentaQueryDTO {
    public Integer getId();
    public String getNombre();
    public String getEstado();
    public String getZonaPermitida();
    public Direccion getDireccion();
}
 