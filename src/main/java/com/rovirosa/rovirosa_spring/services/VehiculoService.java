package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Vehiculo;
import com.rovirosa.rovirosa_spring.repositories.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> getVehiculoRepository() {
        return vehiculoRepository.findAll();
    }
    
}
