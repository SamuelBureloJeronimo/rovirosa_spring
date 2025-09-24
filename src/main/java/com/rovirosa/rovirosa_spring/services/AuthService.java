package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IAuth;

@Service
public class AuthService implements IAuth {

    @Autowired
    private UsuarioRepository userRepo;

    @Override
    public Usuario login(String user, String password) {
        // Buscar por correo y contraseña
        Usuario us = this.userRepo.findByPasswordAndCorreo(password, user);
        if(us != null)
            return us;
        
        // Si no encuentra busca por curp
        us = this.userRepo.findByPasswordAndPersona_Curp(password, user);
        if(us != null)
            return us;
        
        //Si no encuentra busca por Telefono
        us = this.userRepo.findByPasswordAndPersona_Tel(password, user);
        if(us != null)
            return us;
        
        return us;
    }
    
}
