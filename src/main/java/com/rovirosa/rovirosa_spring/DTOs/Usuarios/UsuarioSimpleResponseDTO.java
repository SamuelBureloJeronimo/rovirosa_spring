package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import com.rovirosa.rovirosa_spring.models.Persona;

import lombok.Data;

@Data
public class UsuarioSimpleResponseDTO {

    private Integer id;
    private String correo;
    private String estado;
    private String rol;
    private Integer puntoVenta_Id;

    private Persona persona;

    public UsuarioSimpleResponseDTO(UsuarioQueryDTO usuario) {
        this.id = usuario.getId();
        this.correo = usuario.getCorreo();
        this.estado = usuario.getEstado();
        this.rol = usuario.getRol();
        this.persona = usuario.getPersona();
        if (usuario.getPuntoVenta_Id() != null) {
            this.puntoVenta_Id = usuario.getPuntoVenta_Id();
        } else {
            this.puntoVenta_Id = null;
        }
    }

    public UsuarioSimpleResponseDTO(Integer id, String correo, String estado, String rol, Persona persona) {
        this.id = id;
        this.correo = correo;
        this.estado = estado;
        this.rol = rol;
        this.persona = persona;
    }

}
