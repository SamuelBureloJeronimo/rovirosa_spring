package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import com.rovirosa.rovirosa_spring.models.Persona;

public class UsuarioSimpleResponseDTO implements UsuarioQueryDTO {

    private Integer id;
    private String correo;
    private String estado;
    private String rol;

    private Persona persona;

    public UsuarioSimpleResponseDTO(UsuarioQueryDTO usuario) {
        this.id = usuario.getId();
        this.correo = usuario.getCorreo();
        this.estado = usuario.getEstado();
        this.rol = usuario.getRol();
        this.persona = usuario.getPersona();
    }

    public UsuarioSimpleResponseDTO(Integer id, String correo, String estado, String rol, Persona persona) {
        this.id = id;
        this.correo = correo;
        this.estado = estado;
        this.rol = rol;
        this.persona = persona;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getCorreo() {
        return correo;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public String getRol() {
        return rol;
    }

    @Override
    public Persona getPersona() {
        return persona;
    }
    
}
