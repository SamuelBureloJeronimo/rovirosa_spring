package com.rovirosa.rovirosa_spring.controllers.public_routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.AppInfoDTO;
import com.rovirosa.rovirosa_spring.services.PublicService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private PublicService publicServ;

    // Todos los usuarios
    @GetMapping("/get-app-info")
    public ResponseEntity<ApiResponse<AppInfoDTO>> getInfoApp() {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResponse<>(true, "Info de la app", publicServ.getInfoApp()));
    }

}
