package com.rovirosa.rovirosa_spring.DTOs.Rutas;

import java.math.BigDecimal;

public interface RutaDetalleQueryByRepartidorIdDTO {
    Integer getId();
    Integer getRuta_Id();
    
    Integer getVenta_Id();
    String getVenta_FechaInicio();
    
    String getVenta_Pago_Metodo();
    Double getVenta_Pago_PagaCon();
    Double getVenta_Pago_Monto();

    BigDecimal getLat();
    BigDecimal getLng();
    String getRef();
}
