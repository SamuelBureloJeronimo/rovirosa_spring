package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.repositories.PagoRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IPago;

import jakarta.transaction.Transactional;

@Service
public class PagoService implements IPago {

    @Autowired
    private PagoRepository pagoRep;

    @Transactional
    @Override
    public ApiResponse<Void> updatePagoStatus(Integer id, String status) {
        // Validate status input
        int updatedRows = pagoRep.updatePagoStatus(id, status);
        // Return appropriate ApiResponse based on the update result
        if (updatedRows > 0)
            return new ApiResponse(true, "Pago status updated successfully.", null);
        else 
            return new ApiResponse(false, "Pago not found or status unchanged.", null);
        

    }
    
}
