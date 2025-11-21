package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaStartPutDTO;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaRepository;
import com.rovirosa.rovirosa_spring.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRep;
    @Autowired
    private RepartidorRepository repRep;
    @Autowired
    private VentaRepository ventaRep;
    @Autowired
    private FirebaseService firebaseService;

    public List<RutaQueryDTO> getAllRutasByPvId(Integer pvId) {
        return rutaRep.findRutasActivas(List.of("pendiente", "en_ruta"), pvId);
    }

    @Transactional
    public ApiResponse<String> IniciarRutaRepartidor(RutaStartPutDTO dto) {
        ApiResponse<String> response = new ApiResponse<>();
        // Actualizar el estado del repartdior a "en_ruta".
        Integer result = repRep.updateEstadoRepartidor(dto.getRepId(), "en_ruta");
        if(result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado del repartidor");
            return response;
        }
        System.out.println("Repartidor actualizado a en_ruta");
        // Actualizar el estado de la ruta a "en_ruta".
        result = rutaRep.updateEstadoRuta(dto.getRutaId(), "en_ruta");
        if(result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado de la ruta");
            return response;
        }
        System.out.println("Ruta actualizada a en_ruta");
        // Actualizar el estado de cada venta a "En_camino".
        for(Integer ventaId : dto.getVentasId()) {
            // Llamar al servicio de venta para actualizar el estado.
            result = ventaRep.updateEstadoVenta(ventaId, "En_camino");
            if(result == 0) {
                response.setSuccess(false);
                response.setMessage("No se pudo actualizar el estado de la venta ID: " + ventaId);
                return response;
            }
            System.out.println("Venta ID " + ventaId + " actualizada a En_camino");
            // Notificar al cliente que su pedido está en camino.
            String tokenFmc = ventaRep.findTokenFmcByClienteId(ventaId).orElse(null);
            System.out.println("Token FCM del cliente de la venta ID " + ventaId + ": " + tokenFmc);
            if(tokenFmc != null) {
                firebaseService.sendNotification(
                    tokenFmc,
                    "🚚 Tu pedido está en camino",
                    "El repartidor está en camino. ¡Prepárate para recibir tu pedido!"
                );
                System.out.println("Notificación enviada al cliente de la venta ID " + ventaId);
            }
        }

        System.out.println("Todas las ventas actualizadas y notificaciones enviadas");
        response.setSuccess(true);
        response.setMessage("Ruta iniciada correctamente");
        return response;
    }
    
}
