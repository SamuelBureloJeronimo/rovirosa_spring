package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import com.rovirosa.rovirosa_spring.models.Persona;

public interface UsuarioQueryDTO {
    Integer getId();
    String getCorreo();
    Integer getPuntoVenta_Id();
    String getEstado();
    String getRol();
    Persona getPersona();
}
