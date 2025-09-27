package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.services.ClienteService;

@RestController
@RequestMapping("/api/v1/clients")
public class ClienteController {
    @Autowired
    private ClienteService clientServ;

    @GetMapping("/get-all")
    public List<Cliente> getAllClients() {
        return clientServ.getAll();
    }
}
