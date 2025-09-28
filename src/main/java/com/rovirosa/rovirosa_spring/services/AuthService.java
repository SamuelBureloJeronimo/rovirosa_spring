package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IAuth;
import com.rovirosa.rovirosa_spring.utils.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements IAuth {

    @Autowired
    private UsuarioRepository userRep;
    @Autowired
    private PersonaRepository personRep;
    @Autowired
    private ClienteRepository clientRep;
    @Autowired
    private DireccionRepository dirRep;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Usuario login(String user, String password) {
        // Buscar por correo y contraseña
        Usuario us = this.userRep.findByPasswordAndCorreo(password, user);
        if (us != null)
            return us;

        // Si no encuentra busca por curp
        us = this.userRep.findByPasswordAndPersona_Curp(password, user);
        if (us != null)
            return us;

        // Si no encuentra busca por Telefono
        us = this.userRep.findByPasswordAndPersona_Tel(password, user);
        if (us != null)
            return us;

        return us;
    }

    @Transactional
    @Override
    public String register(Cliente cliente) {
        personRep.save(cliente.getUsuario().getPersona());
        dirRep.save(cliente.getDireccion());
        userRep.save(cliente.getUsuario());
        clientRep.save(cliente);

        return jwtUtil.generateToken(cliente.getUsuario().getCorreo(), cliente.getUsuario().getRol());
    }

    @Override
    public String existUser(Usuario usuario) {
        // Buscar por correo y contraseña
        Boolean us = this.userRep.existsByCorreo(usuario.getCorreo());
        if (us)
            return "El correo proporcionado ya existe.";

        // Si no encuentra busca por curp
        us = this.userRep.existsByPersona_Curp(usuario.getPersona().getCurp());
        if (us)
            return "La curp proporcionada ya existe.";

        // Si no encuentra busca por Telefono
        us = this.userRep.existsByPersona_Tel(usuario.getPersona().getTel());
        if (us)
            return "El teléfono porporcionado ya existe.";

        return "";
    }

    @Override
    public Boolean existCurp(String curp) {
        // Busca por curp
        return this.userRep.existsByPersona_Curp(curp);
    }

    @Override
    public Boolean existTel(String tel) {
        // Busca por Telefono
        return this.userRep.existsByPersona_Tel(tel);
    }

}
