package com.rovirosa.rovirosa_spring.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;

public interface IPago {

    public ApiResponse<Void> updatePagoStatus(Integer idPago, String status);

    public ApiResponse<String> updateComprobante(Integer idPago, MultipartFile archivo);
    
}
