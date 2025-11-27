package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import java.sql.Timestamp;

import com.rovirosa.rovirosa_spring.models.Persona;

public interface MyPerfilDTO {

    Integer getId();
    Persona getPersona();   
    String getCorreo();
    String getEstado();
    String getRol();
    Timestamp getCreated();
}
