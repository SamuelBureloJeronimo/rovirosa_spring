package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.AppInfoDTO;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;

@Service
public class PublicService {

    @Autowired
    private EmpresaConfigRepository configRep;

    public AppInfoDTO getInfoApp() {
        AppInfoDTO config = configRep.findFirstByOrderByRfcAsc();
        if (config == null) {
            System.out.println("No se encontró configuración de la empresa, retornando valores por defecto.");
            return new AppInfoDTO("Rovirosa", "default-logo.jpg");
        }
        System.out.println("Configuración de la empresa encontrada: " + config.toString());
        return config;
    }
    
}
