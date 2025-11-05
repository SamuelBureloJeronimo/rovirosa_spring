package com.rovirosa.rovirosa_spring.controllers.public_routes;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Ocr.OcrFrontDTO;
import com.rovirosa.rovirosa_spring.services.OcrService;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/ine")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateINE(
            @Valid @RequestPart OcrFrontDTO dto,
            @RequestPart("ine") MultipartFile file) {

        try {
            ApiResponse<Map<String, Object>> response = ocrService.validateIneFront(dto, file);
            if (!response.isSuccess()) {
                return ResponseEntity.badRequest()
                        .body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error procesando INE: " + e.getMessage(), null));
        }

    }

    @PostMapping("/ine-reverso")
    public ResponseEntity<ApiResponse<Map<String, Object>>> processINEReverso(
            @Valid @RequestPart OcrFrontDTO dto,
            @RequestPart("ine") MultipartFile file) {
        try {
            ApiResponse<Map<String, Object>> response = ocrService.validateReverso(dto, file);
            if (!response.isSuccess()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false,
                                "Los datos proporcionados no coinciden con la información del INE", null));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error procesando INE: " + e.getMessage(), null));
        }
    }

}
