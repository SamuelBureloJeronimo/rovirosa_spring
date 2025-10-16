package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRep;
    @Autowired
    private StorageService storageService;

    public Marca newBrand(Marca marca, MultipartFile logo) {
        marca.setLogo("marcas/"+storageService.generateFileName());
        Marca m = marcaRep.save(marca);
        if (m.getId() != null)
            storageService.store(logo, "", m.getLogo());
        return m;
    }

    
    public List<Marca> getMarcas() {
        return this.marcaRep.findAll();
    }


    
}
