package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerenteAsignToPvDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaQueryDTO;
import com.rovirosa.rovirosa_spring.models.Ruta;
import com.rovirosa.rovirosa_spring.services.GerentePvService;
import com.rovirosa.rovirosa_spring.services.RutaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/gerente-pv")
public class GerentePvController {

    @Autowired
    private GerentePvService gerentePvService;

    @Autowired
    private RutaService rutaService;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/get-all-rutas/{pvId}")
    public ResponseEntity<ApiResponse<List<RutaQueryDTO>>> getRutasByGerente(@PathVariable Integer pvId) {
        List<RutaQueryDTO> data = rutaService.getAllRutasByPvId(pvId);
        ApiResponse<List<RutaQueryDTO>> apiResponse = new ApiResponse<>(true, "Rutas obtenidas", data);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GerentePvQueryDTO>>> getAllGerentesWithPuntosVenta() {
        List<GerentePvQueryDTO> data = gerentePvService.getAllGerentesPv();
        ApiResponse<List<GerentePvQueryDTO>> apiResponse = new ApiResponse<>(true, "Gerentes con puntos de venta obtenidos", data);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-curp/{curp}")
    public ResponseEntity<ApiResponse<GerentePvQueryDTO>> getGerenteWithPuntosVentaById(
            @PathVariable String curp) {
        GerentePvQueryDTO data = gerentePvService.getGerentePvById(curp);
        if(data == null) {
            ApiResponse<GerentePvQueryDTO> apiResponse = new ApiResponse<>(false, "Gerente no encontrado", null);
            return ResponseEntity.status(404).body(apiResponse);
        }
        ApiResponse<GerentePvQueryDTO> apiResponse = new ApiResponse<>(true, "Gerente con puntos de venta obtenido", data);
        return ResponseEntity.ok(apiResponse);
    }
    

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/assign-pv")
    public ResponseEntity<ApiResponse<String>> assignPuntoVentaToGerente(
            @Valid @RequestBody GerenteAsignToPvDTO dto) {
        gerentePvService.assignPuntoVentaToGerente(dto.getGerenteId(), dto.getPvId());
        ApiResponse<String> apiResponse = new ApiResponse<>(true, "Punto de venta asignado al gerente", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/remove-pv/{id}")
    public ResponseEntity<ApiResponse<String>> removePuntoVentaFromGerente(
            @PathVariable Integer id) {
        System.out.println("ID recibido: " + id);
        gerentePvService.removePuntoVentaFromGerente(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(true, "Punto de venta removido del gerente", null);
        return ResponseEntity.ok(apiResponse);
    }

}
