package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteIneQueryDTO;

public interface ICliente {

    public ApiResponse<ClienteIneQueryDTO> findIneById(Integer id);

}
