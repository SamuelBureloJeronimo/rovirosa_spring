package com.rovirosa.rovirosa_spring.DTOs.Rutas;

import java.math.BigDecimal;

public interface RutaDetalleQueryByRepartidorIdDTO {
    Integer getId();
    Integer getRuta_Id();
    String getRuta_Estado();
    
    Integer getVenta_Id();
    String getVenta_Estado();
    String getVenta_FechaInicio();
    
    String getVenta_Pago_Metodo();
    Double getVenta_Pago_PagaCon();
    Double getVenta_Pago_Monto();
    String getVenta_Pago_Compr();
    String getVenta_Pago_Estado();

    BigDecimal getLat();
    BigDecimal getLng();
    String getRef();
}
