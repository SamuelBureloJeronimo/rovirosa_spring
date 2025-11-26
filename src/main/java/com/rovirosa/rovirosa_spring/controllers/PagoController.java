package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.services.PagoService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PreAuthorize("hasAnyRole('ADMIN','REPARTIDOR')")
    @PutMapping("/update-status/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePagoStatus(@PathVariable Integer id, @RequestBody String status) {
        ApiResponse<Void> response = pagoService.updatePagoStatus(id, status);

        if(response.isSuccess())
            return ResponseEntity.ok(response);
        else 
            return ResponseEntity.status(400).body(response);
        
    }
    
}
