package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigUpdateDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.EmpresaConfigQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassDTO;
import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassPutDTO;
import com.rovirosa.rovirosa_spring.models.DatoTransferencia;
import com.rovirosa.rovirosa_spring.services.EmpresaConfigService;

import jakarta.validation.Valid;

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

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @GetMapping("/cuenta")
    public ResponseEntity<ApiResponse<DatoTransferencia>> getCuentaTransferencia() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Datos de transferencia obtenidos con éxito",
                        empresaConfigService.getCuentaTransferencia()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/cuenta")
    public ResponseEntity<ApiResponse<String>> updateCuentaTransferencia(
            @RequestBody DatoTransferencia datoTransferencia) {
        // Lógica para actualizar los datos de transferencia bancaria
        // (No implementada en el servicio según los snippets proporcionados)
        empresaConfigService.saveCuentaTransferencia(datoTransferencia);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Datos de transferencia actualizados con éxito", null));
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
        if (rowsAffected == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, "No se pudo actualizar la configuración de Gmail", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración de Gmail actualizada con éxito", null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateConfig(
            @Valid @RequestPart("dto") EmpresaConfigUpdateDTO dto,
            @RequestPart(name = "logo", required = false) MultipartFile logo) {

        // Lógica para actualizar la configuración de la empresa
        ApiResponse<Void> response = empresaConfigService.updateConfig(dto, logo);

        // Verificar si la actualización fue exitosa
        if(!response.isSuccess())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(false, "No se pudo actualizar la configuración de la empresa", null));
        // Responder con éxito
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Configuración actualizada con éxito", null));
    }

}
