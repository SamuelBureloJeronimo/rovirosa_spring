package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.BannerQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.PromocionesDTO;
import com.rovirosa.rovirosa_spring.services.DescuentoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<PromocionesDTO>> getDescuentos() {
        PromocionesDTO descuentos = descuentoService.getDescuentos();
        ApiResponse<PromocionesDTO> apiResponse = new ApiResponse<>(true, "Descuentos retrieved successfully", descuentos);
        return ResponseEntity.ok(apiResponse);
    }
    

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<Object>> getAllBanners() {
        ApiResponse<Object> apiResponse;
        List<BannerQueryDTO> banners = descuentoService.getAllBanners();
        apiResponse = new ApiResponse<>(true, "Banners retrieved successfully", banners);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createDescuento(
            @Valid @RequestPart DescuentoPostDTO dto,
            @RequestPart(value = "banner", required = false) MultipartFile banner) {

        ApiResponse<String> apiResponse;

        if (dto.getObjetivo().equalsIgnoreCase("PRODUCTO")) {
            Boolean result = descuentoService.aplicarToProducto(dto, banner);
            if(result){
                apiResponse = new ApiResponse<String>(true, "Descuento aplicado a producto", null);
            } else {
                apiResponse = new ApiResponse<String>(false, "Error al aplicar descuento a producto", null);
                return ResponseEntity
                .notFound().build();
            }
        } else if (dto.getObjetivo().equalsIgnoreCase("MARCA")) {
            descuentoService.aplicarToMarca(dto, banner);
            apiResponse = new ApiResponse<String>(true, "Descuento aplicado a marca", null);
        } else if (dto.getObjetivo().equalsIgnoreCase("CATEGORIA")) {
            descuentoService.aplicarToCategoria(dto, banner);
            apiResponse = new ApiResponse<String>(true, "Descuento aplicado a categoria", null);
        } else {
            apiResponse = new ApiResponse<String>(false, "Objetivo de descuento inválido", null);
        }

        ResponseEntity<ApiResponse<String>> response = ResponseEntity
                .ok(apiResponse);

        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idConfig}")
    public ResponseEntity<ApiResponse<String>> eliminarDescuento(@PathVariable Integer idConfig) {
        descuentoService.eliminarDescuento(idConfig);
        ApiResponse<String> apiResponse = new ApiResponse<>(true, "Descuento eliminado exitosamente", null);
        return ResponseEntity.ok(apiResponse);
    }

}
