package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioSimpleResponseDTO;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.services.RepartidorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioSimpleResponseDTO>> postMethodName(@RequestBody RepartidorPostDTO dto) {
        Repartidor repartidor = repartidorService.createRepartidor(dto);

        if (repartidor == null)
            return ResponseEntity.ok(new ApiResponse<>(false, "Error al crear repartidor", null));

        UsuarioSimpleResponseDTO repartidorDTO = new UsuarioSimpleResponseDTO(
                repartidor.getUsuario().getId(),
                repartidor.getUsuario().getCorreo(),
                repartidor.getUsuario().getEstado(),
                repartidor.getUsuario().getRol(),
                repartidor.getUsuario().getPersona());

        return ResponseEntity.ok(new ApiResponse<>(true, "Success", repartidorDTO));
    }

}
