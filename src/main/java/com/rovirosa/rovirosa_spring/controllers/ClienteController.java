package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteIneQueryDTO;
import com.rovirosa.rovirosa_spring.services.ClienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/ine/{clienteId}")
    public ResponseEntity<ApiResponse<ClienteIneQueryDTO>> getClienteByIne(@PathVariable Integer clienteId) {
        ApiResponse<ClienteIneQueryDTO> cliente = clienteService.findIneById(clienteId);
        if (!cliente.isSuccess())
            return ResponseEntity.status(404).body(cliente);
        return ResponseEntity.ok(cliente);
    }

}