package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteIneQueryDTO;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.ICliente;

@Service
public class ClienteService implements ICliente {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ApiResponse<ClienteIneQueryDTO> findIneById(Integer id) {
        ClienteIneQueryDTO clienteIne = clienteRepository.findIneById(id);
        if(clienteIne != null) {
            return new ApiResponse<>(true, "Client found", clienteIne);
        } else {
            return new ApiResponse<>(false, "Client not found", null);
        }
    }

    
}
