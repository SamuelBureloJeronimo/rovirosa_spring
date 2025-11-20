package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Vehiculo;
import com.rovirosa.rovirosa_spring.services.VehiculoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Vehiculo>> createVehiculo(
        @Valid @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(
            new ApiResponse<Vehiculo>(
                true,
                "Vehículo creado correctamente",
                vehiculoService.saveVehiculo(vehiculo)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Vehiculo>>> getAllVehiculos() {
        return ResponseEntity.ok(
                new ApiResponse<List<Vehiculo>>(
                        true,
                        "Vehiculos obtenidos correctamente",
                        vehiculoService.getVehiculoRepository()));
    }

}
