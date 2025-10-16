package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.services.CategoriaService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaServ;

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> getAllCategorias() {
        List<Categoria> categorias = categoriaServ.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Categorias obtenidas exitosamente", categorias));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>> create(@RequestParam String nombre) {
        Categoria nuevaCategoria = categoriaServ.newCateg(nombre);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Categoria creada exitosamente", nuevaCategoria));
    }
    

}
