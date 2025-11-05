package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionPutDTO;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.services.DireccionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/direccion")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'REPARTIDOR')")
    @GetMapping("/{UserId}")
    public ResponseEntity<ApiResponse<Direccion>> get(@PathVariable Integer UserId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Dirección obtenida exitosamente", direccionService.getDireccionByUserId(UserId)));
    }
    

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCoords(@Valid @RequestBody DireccionPutDTO dto) {

        Boolean rowAffected = direccionService.updateCoords(dto);

        if (!rowAffected)
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, "No se pudieron actualizar las coordenadas.", null)
            );

        return ResponseEntity.status(200).body(
            new ApiResponse<>(true, "Coordenadas actualizadas exitosamente.", null)
        );
    }

}
