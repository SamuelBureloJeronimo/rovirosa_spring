package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;
import com.rovirosa.rovirosa_spring.models.Cliente;

public interface ICliente {

    public List<Cliente> getAll();
    public String register(Cliente cliente);
    
}
