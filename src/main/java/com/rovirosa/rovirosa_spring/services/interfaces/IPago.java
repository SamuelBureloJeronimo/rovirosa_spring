package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;

public interface IPago {

    public ApiResponse<Void> updatePagoStatus(Integer idPago, String status);
    
}
