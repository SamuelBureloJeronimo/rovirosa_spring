package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.services.RepartidorService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'REPARTIDOR')")
    @GetMapping("/rutas")
    public ResponseEntity<ApiResponse<String>> getDistancia(){
        //double distancia = repartidorService.calcularDistancia(17.759576223228915, -92.60354532756537, 17.758046253795616, -92.60054111480713);
        return ResponseEntity.ok(new ApiResponse<>(true, "Distancia calculada exitosamente", 1 + " km"));
    }
    
    


}
