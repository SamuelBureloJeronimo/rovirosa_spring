package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.models.Usuario;

public interface IAuth {

    public Usuario login(String user, String password);
    
}
