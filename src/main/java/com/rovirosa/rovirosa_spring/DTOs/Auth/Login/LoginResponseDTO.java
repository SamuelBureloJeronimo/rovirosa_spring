package com.rovirosa.rovirosa_spring.DTOs.Auth.Login;

import com.rovirosa.rovirosa_spring.utils.JwtUtil;

public class LoginResponseDTO {

    private Integer id;
    private Integer clienteId;
    private Integer repartidorId;
    private Integer puntoVenta_Id;
    private String token;
    private String estado;
    private String rol;

    public LoginResponseDTO(LoginQueryDTO dto, JwtUtil jwtUtil, Integer clienteId) {
        if(dto.getEstado().equals("activo"))
            this.token = jwtUtil.generateToken(dto.getCorreo(), dto.getRol());
        this.id = dto.getId();
        this.clienteId = clienteId;
        this.puntoVenta_Id = dto.getPuntoVenta_Id();
        this.estado = dto.getEstado();
        this.rol = dto.getRol();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepartidorId() {
        return repartidorId;
    }
    public void setRepartidorId(Integer repartidorId) {
        this.repartidorId = repartidorId;
    }

    public Integer getPuntoVenta_Id() {
        return puntoVenta_Id;
    }
    public void setPuntoVenta_Id(Integer puntoVenta_Id) {
        this.puntoVenta_Id = puntoVenta_Id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
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
