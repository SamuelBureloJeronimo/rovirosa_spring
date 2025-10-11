package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;

@Service
public class CategoriasService {

    @Autowired
    private CategoriaRepository categRep;

    
    public Categoria newCateg(Categoria categ) {
        return categRep.save(categ);
    }

}
