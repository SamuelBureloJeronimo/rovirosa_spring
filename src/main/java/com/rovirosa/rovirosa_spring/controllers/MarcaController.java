package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.services.MarcaService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaServ;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Marca>> newBrand(@RequestParam String nombre, @RequestParam Integer categ_id,
            @RequestPart("logo") MultipartFile logo) {

        Categoria categ = new Categoria();
        categ.setId(categ_id);

        Marca marca = new Marca();
        marca.setNombre(nombre);
        marca.setCategoria(categ);

        Marca res = marcaServ.newBrand(marca, logo);

        return ResponseEntity.status(200).body(
            new ApiResponse<>(true, "Marca registrada exitosamente", res)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Marca>>> getAllMarcas() {
        List<Marca> marcas = marcaServ.getMarcas();
        return ResponseEntity.ok().body(
            new ApiResponse<>(true, "Marcas obtenidas exitosamente", marcas)
        );
    }
    

}
