package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.models.Pago;
import com.rovirosa.rovirosa_spring.repositories.PagoRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IPago;

import jakarta.transaction.Transactional;

@Service
public class PagoService implements IPago {

    @Autowired
    private PagoRepository pagoRep;
    @Autowired
    private StorageService storageService;

    @Transactional
    @Override
    public ApiResponse<Void> updatePagoStatus(Integer id, String status) {
        // Validate status input
        int updatedRows = pagoRep.updatePagoStatus(id, status);
        // Return appropriate ApiResponse based on the update result
        if (updatedRows > 0)
            return new ApiResponse<>(true, "Pago status updated successfully.", null);
        else
            return new ApiResponse<>(false, "Pago not found or status unchanged.", null);
    }

    @Transactional
    @Override
    public ApiResponse<String> updateComprobante(Integer idPago, MultipartFile archivo) {
        Pago pago = pagoRep.findById(idPago)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago.getCompr() != null && !pago.getCompr().isEmpty()) {

            String archivoName = pago.getCompr().replaceAll("comprobantes/", "");
            System.out.println(archivoName);
            storageService.store(archivo, "comprobantes/", archivoName);
            return new ApiResponse<>(true, "Comprobante updated successfully", archivoName);
        } else {
            String randomName = storageService.generateFileName();
            String archivoName = storageService.store(archivo, "comprobantes/", randomName);
            pago.setCompr("comprobantes/" + randomName);
            pagoRep.save(pago);
            return new ApiResponse<>(true, "Comprobante uploaded successfully", archivoName);
        }

    }

}
