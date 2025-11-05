package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteQueryDireccionDTO;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionPutDTO;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRep;
    @Autowired
    private ClienteRepository clienteRep;

    public Direccion getDireccionByUserId(Integer userId) {
        ClienteQueryDireccionDTO cliente = clienteRep.findByUsuario_Id(userId);
        return cliente.getDireccion();
    }
    
    public Direccion updateDireccion(Direccion direccion) {
        return direccionRep.save(direccion);
    }

    @Transactional
    public Boolean updateCoords(DireccionPutDTO coords) {
        return direccionRep.updateCoordenadas(coords.getId(), coords.getLatitud(), coords.getLongitud(), coords.getRef()) > 0;
    }
    
}
