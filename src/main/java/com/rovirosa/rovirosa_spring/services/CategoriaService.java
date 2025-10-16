package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categRep;

    
    public Categoria newCateg(String nombre) {
        Categoria categ = new Categoria();
        categ.setNombre(nombre);
        return categRep.save(categ);
    }

    public List<Categoria> getAll() {
        return categRep.findAll();
    }

}
