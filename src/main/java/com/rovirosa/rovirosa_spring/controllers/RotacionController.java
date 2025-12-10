package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignSimpleDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignacionQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RotacionPostDTO;
import com.rovirosa.rovirosa_spring.services.RotacionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/rotaciones")
public class RotacionController {

    @Autowired
    private RotacionService rotacionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RepartidorAsignacionQueryDTO>>> getRotacion(
            @RequestParam(required = false) Integer dummyParam) {
        List<RepartidorAsignacionQueryDTO> repartidores = rotacionService.getRotacion();
        return ResponseEntity.ok(new ApiResponse<>(true, "Rotación procesada exitosamente", repartidores));
    }

    @PreAuthorize("hasAnyRole('ADMIN','REPARTIDOR')")
    @GetMapping("/by-rep/{id}")
    public ResponseEntity<ApiResponse<RepartidorAsignSimpleDTO>> getRotacionById(@PathVariable Integer id) {
        RepartidorAsignSimpleDTO repartidor = rotacionService.getRotacionById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rotación procesada exitosamente", repartidor));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> asignToPv(@RequestBody RotacionPostDTO dto) {

        ApiResponse<String> response = rotacionService.asignToPv(dto);
        
        if(response.isSuccess() == false)
            return ResponseEntity.badRequest().body(response);

        return ResponseEntity.ok(new ApiResponse<>(true, "Repartidor creado exitosamente", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarRotacion(@PathVariable Integer id) {
        rotacionService.eliminarRotacion(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rotación eliminada exitosamente", null));
    }

}
