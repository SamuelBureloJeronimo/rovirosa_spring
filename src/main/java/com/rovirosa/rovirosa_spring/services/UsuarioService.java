package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioSimpleResponseDTO;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EntityManager entityManager;

    public List<UsuarioSimpleResponseDTO> getUsuariosByRol(String rol) {

        List<UsuarioQueryDTO> usuarios = usuarioRepository.findByRol(rol);
        List<UsuarioSimpleResponseDTO> result = new ArrayList<>();
        
        for (UsuarioQueryDTO usuarioQueryDTO : usuarios) {
            result.add(new UsuarioSimpleResponseDTO(usuarioQueryDTO));
        }
        
        return result;

    }

    @Transactional
    public void updatePuntoDeVenta(Integer pvId, Integer userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PuntoVenta pvRef = entityManager.getReference(PuntoVenta.class, pvId);
        usuario.setPuntoVenta(pvRef);
        usuarioRepository.save(usuario);
    }

}
