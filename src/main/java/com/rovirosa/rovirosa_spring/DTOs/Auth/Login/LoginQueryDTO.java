package com.rovirosa.rovirosa_spring.DTOs.Auth.Login;

public interface LoginQueryDTO {
    Integer getId();
    String getRol();
    String getTokenFmc();
    Integer getPuntoVenta_Id();
    Integer getPuntoVenta_Config_MontoMin();
    String getCorreo();
    String getEstado();
}