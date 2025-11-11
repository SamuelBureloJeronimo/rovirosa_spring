package com.rovirosa.rovirosa_spring.DTOs.Rutas;

import java.math.BigDecimal;

public interface RutaDetalleQueryByRepartidorIdDTO {
    Integer getId();
    Integer getVenta_Id();
    BigDecimal getLat();
    BigDecimal getLng();
}
