package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IUsuario;

@Service
public class UsuarioService implements IUsuario {

    @Autowired
    UsuarioRepository userRepo;
    @Autowired
    PersonaRepository persRep;

    @Transactional
    @Override
    public Usuario create(Usuario usuario) {
        Persona per = this.persRep.save(usuario.getPersona());
        per.printConsole();
        usuario.setPersona(per);
        return this.userRepo.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public String existUser(Usuario usuario) {
        // Buscar por correo y contraseña
        Boolean us = this.userRepo.existsByCorreo(usuario.getCorreo());
        if (us)
            return "El correo proporcionado ya existe.";

        // Si no encuentra busca por curp
        us = this.userRepo.existsByPersona_Curp(usuario.getPersona().getCurp());
        if (us)
            return "La curp proporcionada ya existe.";

        // Si no encuentra busca por Telefono
        us = this.userRepo.existsByPersona_Tel(usuario.getPersona().getTel());
        if (us)
            return "El teléfono porporcionado ya existe.";

        return "";
    }

    @Override
    public Boolean existCurp(String curp) {
        // Busca por curp
        return this.userRepo.existsByPersona_Curp(curp);
    }

    @Override
    public Boolean existTel(String tel) {
        // Busca por Telefono
        return this.userRepo.existsByPersona_Tel(tel);
    }

}
