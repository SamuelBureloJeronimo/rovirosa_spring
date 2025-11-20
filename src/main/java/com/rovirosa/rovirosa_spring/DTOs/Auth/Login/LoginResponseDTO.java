package com.rovirosa.rovirosa_spring.DTOs.Auth.Login;

import com.rovirosa.rovirosa_spring.utils.JwtUtil;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private Integer id;
    private Integer clienteId;
    private Integer repartidorId;
    private Integer puntoVenta_Id;
    private String token;
    private String estado;
    private String rol;
    private Integer montoMin;

    public LoginResponseDTO(LoginQueryDTO dto, JwtUtil jwtUtil, Integer clienteId) {
        if(dto.getEstado().equals("activo"))
            this.token = jwtUtil.generateToken(dto.getCorreo(), dto.getRol());
        this.id = dto.getId();
        this.clienteId = clienteId;
        this.puntoVenta_Id = dto.getPuntoVenta_Id();
        this.estado = dto.getEstado();
        this.rol = dto.getRol();
        this.montoMin = dto.getPuntoVenta_Config_MontoMin();
    }

}
