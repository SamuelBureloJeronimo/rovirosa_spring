package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.services.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductoController {

        @Autowired
        private ProductoService productoService;

        @PreAuthorize("hasAnyRole('ADMIN','CLIENTE','REPARTIDOR')")
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Producto>> getById(@PathVariable Integer id) {
                Producto producto = productoService.getProductById(id);
                if (producto == null) {
                        return ResponseEntity.status(404).body(
                                        new ApiResponse<>(false, "No se encontró el producto.", null));
                }
                return ResponseEntity.status(200).body(
                                new ApiResponse<>(true, "Producto obtenido exitosamente.", producto));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<ApiResponse<ProductoResponseDTO>> create(
                        @Valid @RequestPart ProductoCreateDTO dto,
                        @RequestPart("imagen") MultipartFile imagen) {

                ProductoResponseDTO nuevo = productoService.create(dto, imagen);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(true, "Producto creado correctamente", nuevo));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping
        public ResponseEntity<ApiResponse<ProductoResponseDTO>> update(
                        @Valid @RequestPart ProductoUpdateDTO dto,
                        @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

                System.out.println("ID: " + dto.getId());
                System.out.println("Nombre: " + dto.getNombre());
                System.out.println("Marca ID: " + dto.getMarcaId());
                System.out.println("Precio: " + dto.getPrecio());
                System.out.println("Peso KG: " + dto.getPesoKg());
                System.out.println("Volumen M3: " + dto.getVolM3());

                ProductoResponseDTO actualizado = productoService.update(dto, imagen);

                return ResponseEntity.ok(
                                new ApiResponse<>(true, "Producto actualizado correctamente", actualizado));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
                if (productoService.delete(id)) {
                        return ResponseEntity.ok(new ApiResponse<>(true, "Producto eliminado correctamente", null));
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ApiResponse<>(false, "Producto no encontrado", null));
                }
        }

}
