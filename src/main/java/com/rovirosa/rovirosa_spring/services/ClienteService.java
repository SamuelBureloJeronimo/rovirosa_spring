package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.ICliente;
import com.rovirosa.rovirosa_spring.utils.JwtUtil;

@Service
public class ClienteService implements ICliente {

    @Autowired
    private ClienteRepository clienteRep;
    @Autowired
    private UsuarioRepository usuarioRep;
    @Autowired
    private PersonaRepository personRep;
    @Autowired
    private DireccionRepository dirRep;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Cliente> getAll() {
        return clienteRep.findAll();
    }

    @Transactional
    @Override
    public String register(Cliente cliente) {
        personRep.save(cliente.getUsuario().getPersona());
        dirRep.save(cliente.getDireccion());
        usuarioRep.save(cliente.getUsuario());
        clienteRep.save(cliente);

        return jwtUtil.generateToken(cliente.getUsuario().getCorreo(), cliente.getUsuario().getRol());
    }
    
}
