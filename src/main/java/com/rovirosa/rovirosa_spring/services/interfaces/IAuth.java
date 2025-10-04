package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Usuario;

public interface IAuth {
    
    public String register(Cliente cliente);
    public Usuario login(String user, String password);
    public String existUser(Usuario usuario);
    public Boolean existCurp(String curp);
    public Boolean existTel(String tel);
    public List<String> getCoords();
    
}
