package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.Usuario;

public interface IUsuario {

    public Usuario create(Usuario usuario);

    public String existUser(Usuario usuario);

    public List<Usuario> getAllUsers();
    
}
