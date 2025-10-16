package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigPutDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassPutDTO;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;

import jakarta.transaction.Transactional;

@Service
public class EmpresaConfigService {

    @Autowired
    private EmpresaConfigRepository configRep;
    @Autowired
    private StorageService storageService;

    public GmailPassDTO getGmailConfig() {
        return configRep.findFirstBy();
    }

    @Transactional
    public Integer updateGmailApp(GmailPassPutDTO gmailPassDTO) {
        return configRep.updateGmailConfig(gmailPassDTO.getEmailApp(), gmailPassDTO.getCodigoApp());
    }

    public EmpresaConfigQueryDTO getConfig() {
        return configRep.findProjectedBy();
    }

    public void updateConfig(EmpresaConfigPutDTO configDTO, MultipartFile logo) {
        EmpresaConfig existingConfig = configRep.findAll().stream().findFirst().orElse(null);

        if (configDTO.getRfc() != null)
            existingConfig.setRfc(configDTO.getRfc());

        if (configDTO.getNombre() != null)
            existingConfig.setNombre(configDTO.getNombre());

        if (configDTO.getDescrip() != null)
            existingConfig.setDescrip(configDTO.getDescrip());

        if (configDTO.getMontoMin() != null)
            existingConfig.setMontoMin(configDTO.getMontoMin());

        if (logo != null) {
            System.out.println("Updating logo to: " + logo.getOriginalFilename());
            if (!logo.isEmpty()){
                System.out.println("Storing new logo file: " + existingConfig.getLogo());
                storageService.store(logo, "", existingConfig.getLogo());
            }
        }

        configRep.save(existingConfig);
    }

    public EmpresaConfig update(EmpresaConfig config) {
        return configRep.save(config);
    }

}
