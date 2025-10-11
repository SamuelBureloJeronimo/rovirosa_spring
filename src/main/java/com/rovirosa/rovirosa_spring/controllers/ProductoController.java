package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoCreateDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoUpdateDTO;
import com.rovirosa.rovirosa_spring.services.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponseDTO>> create(
            @Valid @RequestPart ProductoCreateDTO dto,
            @RequestPart("imagen") MultipartFile imagen) {

        ProductoResponseDTO nuevo = productoService.create(dto, imagen);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Producto creado correctamente", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestPart ProductoUpdateDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        dto.setId(id);
        ProductoResponseDTO actualizado = productoService.update(dto, imagen);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Producto actualizado correctamente", actualizado));
    }
}
