package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioSimpleResponseDTO;
import com.rovirosa.rovirosa_spring.services.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{rol}")
    public ResponseEntity<ApiResponse<List<UsuarioSimpleResponseDTO>>> getUsuariosByRol(@PathVariable String rol) {
        List<UsuarioSimpleResponseDTO> usuarios = usuarioService.getUsuariosByRol(rol);
        ApiResponse<List<UsuarioSimpleResponseDTO>> response = new ApiResponse<>(true, "Usuarios obtenidos exitosamente", usuarios);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN','CLIENTE')")
    @PutMapping("/update-pv/{user_id}/{pv_id}")
    public ResponseEntity<ApiResponse<UsuarioQueryDTO>> updatePv(@PathVariable Integer pv_id, @PathVariable Integer user_id) {
        usuarioService.updatePuntoDeVenta(pv_id, user_id);
        ApiResponse<UsuarioQueryDTO> response = new ApiResponse<>(true, "Punto de venta actualizado exitosamente", null);
        return ResponseEntity.ok(response);
    }
    

}
