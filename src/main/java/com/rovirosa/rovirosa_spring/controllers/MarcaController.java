package com.rovirosa.rovirosa_spring.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.services.MarcaService;

@RestController
@RequestMapping("/api/marca")
public class MarcaController {

    @Autowired
    private MarcaService marcaServ;

    @PostMapping("/crear")
    public ResponseEntity<HashMap<String, Marca>> newBrand(@RequestParam String nombre, @RequestParam Integer categ_id,
            @RequestPart("logo") MultipartFile logo) {
        HashMap<String, Marca> response = new HashMap<>();
        Marca marca = new Marca();
        marca.setNombre(nombre);
        Categoria categ = new Categoria();
        categ.setId(categ_id);
        marca.setCategoria(categ);
        Marca marcaReg = marcaServ.newBrand(marca, logo);
        response.put("success", marcaReg);
        return ResponseEntity.status(200).body(response);
    }

}
