package com.rovirosa.rovirosa_spring.DTOs.Auth.Login;

public interface LoginQueryDTO {
    Integer getId();
    String getRol();
    String getTokenFmc();
    Integer getPuntoVenta_Id();
    Integer getPuntoVenta_Config_MontoMin();
    Integer getPuntoVenta_Config_Comision();
    String getCorreo();
    String getEstado();
}