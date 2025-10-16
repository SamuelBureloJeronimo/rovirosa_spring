package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigPutDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassPutDTO;
import com.rovirosa.rovirosa_spring.services.EmpresaConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/empresa-config")
public class EmpresaConfigController {

    @Autowired
    private EmpresaConfigService empresaConfigService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<EmpresaConfigQueryDTO>> getConfig() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración obtenida con éxito", empresaConfigService.getConfig()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/gmail-app")
    public ResponseEntity<ApiResponse<GmailPassDTO>> getGmailConfig() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración de Gmail obtenida con éxito",
                        empresaConfigService.getGmailConfig()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/gmail-app")
    public ResponseEntity<ApiResponse<String>> updateGmailConfig(@RequestBody GmailPassPutDTO gmailPassDTO) {
        Integer rowsAffected = empresaConfigService.updateGmailApp(gmailPassDTO);
        if(rowsAffected == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(false, "No se pudo actualizar la configuración de Gmail", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración de Gmail actualizada con éxito", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateConfig(
            @RequestParam(required = false) String rfc, @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer montoMin,
            @RequestParam(required = false) String descrip,
            @RequestPart(name = "logo", required = false) MultipartFile logo) {

        System.out.println("rfc: " + rfc);
        System.out.println("nom: " + nom);
        System.out.println("montoMin: " + montoMin);
        System.out.println("descrip: " + descrip);
        System.out.println("logo: " + (logo != null ? logo.getOriginalFilename() : "null"));

        EmpresaConfigPutDTO configDTO = new EmpresaConfigPutDTO();
        configDTO.setRfc(rfc);
        configDTO.setNombre(nom);
        configDTO.setMontoMin(montoMin);
        configDTO.setDescrip(descrip);

        empresaConfigService.updateConfig(configDTO, logo);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración actualizada con éxito", null));
    }

}
