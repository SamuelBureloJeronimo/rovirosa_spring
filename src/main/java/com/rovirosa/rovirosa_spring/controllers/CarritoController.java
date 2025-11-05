package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPutDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoResponseDTO;
import com.rovirosa.rovirosa_spring.services.CarritoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<ApiResponse<CarritoQueryByClienteDTO>> addToCart(@RequestBody CarritoPostDTO dto) {
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Producto agregado al carrito exitosamente", carritoService.createCarrito(dto)));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCart(@RequestBody CarritoPutDTO dto) {
        carritoService.updateCarrito(dto.getId(), dto.getCantidad());
        return ResponseEntity.ok(new ApiResponse<>(true, "Producto actualizado en el carrito exitosamente", null));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{userId}/{catalogoId}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(@PathVariable Integer userId, @PathVariable Integer catalogoId) {
        boolean rowAffected = carritoService.removeFromCart(userId, catalogoId);
        if (rowAffected) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto eliminado del carrito exitosamente", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Carrito no encontrado", null));
        }
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CarritoResponseDTO>>> getCarritoByUserId(@PathVariable Integer userId) {
        // Lógica para obtener el carrito por ID de cliente
        List<CarritoResponseDTO> carrito = carritoService.getCarritoByUserId(userId);
        if (carrito != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Carrito obtenido exitosamente", carrito));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Carrito no encontrado", null));
        }
    }

}
