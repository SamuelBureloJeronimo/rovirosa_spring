package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/user")
public class UsuarioController {

    @Autowired
    private UsuarioService userServ;


    @GetMapping("/get-all")
    public List<Usuario> getAllUsers() {
        return userServ.getAllUsers();
    }
}
