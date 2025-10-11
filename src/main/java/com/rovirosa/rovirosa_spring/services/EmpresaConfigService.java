package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;

@Service
public class EmpresaConfigService {

    @Autowired
    private EmpresaConfigRepository configRep;

    
    public EmpresaConfig update(EmpresaConfig config) {
        return configRep.save(config);
    }
    
}
