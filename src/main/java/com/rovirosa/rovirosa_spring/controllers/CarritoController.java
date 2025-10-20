package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPostOrPutDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.services.CarritoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addToCart(@RequestBody CarritoPostOrPutDTO dto) {
        carritoService.agregarAlCarrito(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Producto agregado al carrito exitosamente", null));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CarritoQueryByClienteDTO>>> getCartByClientId(@PathVariable Integer userId) {
        // Lógica para obtener el carrito por ID de cliente
        List<CarritoQueryByClienteDTO> carrito = carritoService.getCarritoByUserId(userId);
        if (carrito != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Carrito obtenido exitosamente", carrito));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Carrito no encontrado", null));
        }
    }

}
