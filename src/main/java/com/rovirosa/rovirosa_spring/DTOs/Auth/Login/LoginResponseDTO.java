package com.rovirosa.rovirosa_spring.DTOs.Auth.Login;

import com.rovirosa.rovirosa_spring.utils.JwtUtil;

public class LoginResponseDTO {

    private String token;
    private String correo;
    private String estado;
    private String rol;

    public LoginResponseDTO(LoginQueryDTO dto, JwtUtil jwtUtil) {
        if(dto.getEstado().equals("activo"))
            this.token = jwtUtil.generateToken(dto.getCorreo(), dto.getRol());
        this.correo = dto.getCorreo();
        this.estado = dto.getEstado();
        this.rol = dto.getRol();
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
