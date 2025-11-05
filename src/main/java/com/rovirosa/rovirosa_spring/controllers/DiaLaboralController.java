package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralResponseDTO;
import com.rovirosa.rovirosa_spring.services.DiaLaboralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/dia-laboral")
public class DiaLaboralController {

    @Autowired
    private DiaLaboralService diaLaboralService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'REPARTIDOR')")
    @GetMapping("/{pvId}")
    public ResponseEntity<ApiResponse<List<DiaLaboralResponseDTO>>> getDiasLaboralesByPuntoVentaId(
            @PathVariable Integer pvId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(true, "Días laborales obtenidos exitosamente",
                        diaLaboralService.getDiasLaboralesByPuntoVentaId(pvId)));
    }

}
