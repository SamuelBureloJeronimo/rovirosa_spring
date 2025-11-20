package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorUpdatePosDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rotacion.RepartidorAsignacionQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Vehiculo.VehiculoAsignToRepDTO;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.services.RepartidorService;
import com.rovirosa.rovirosa_spring.services.RotacionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private RotacionService rotacionService;

    @PutMapping("/position/{repId}")
    @PreAuthorize("hasRole('REPARTIDOR')")
    public ResponseEntity<ApiResponse<Integer>> actualizarPosicionRepartidor(
        @PathVariable Integer repId,
        @Valid @RequestBody RepartidorUpdatePosDTO dto) {
        Integer result = repartidorService.actualizarPosicionRepartidor(repId, dto);
        if (result == 1) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Posición actualizada correctamente", result));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error al actualizar posición", null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/vehiculo")
    public ResponseEntity<ApiResponse<Integer>> asignarVehiculoRepartidor(
        @Valid @RequestBody VehiculoAsignToRepDTO dto) {
            System.out.println(dto.getRepartidorId() + " " + dto.getVehiculoId());
        Integer result = repartidorService.asignVehiculoRepartidor(dto.getRepartidorId(), dto.getVehiculoId());
        if (result == 1) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Vehículo asignado correctamente", result));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error al asignar vehículo", null));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/by-pv/{pvId}")
    public ResponseEntity<ApiResponse<List<RepartidorAsignacionQueryDTO>>> getRepartidoresByPvId(@PathVariable Integer pvId){
        List<RepartidorAsignacionQueryDTO> repartidores = rotacionService.getRotacionByPvId(pvId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Repartidores obtenidos exitosamente", repartidores));
    }   
    

    @PreAuthorize("hasAnyRole('ADMIN', 'REPARTIDOR','GERENTE')")
    @GetMapping("/rutas/{repartidorId}")
    public ResponseEntity<ApiResponse<List<RutaDetalleResponseDTO>>> getDistancia(@PathVariable Integer repartidorId){
        return ResponseEntity.ok(new ApiResponse<>(true, "Distancia calculada exitosamente", repartidorService.getRutaDetalleByRepartidorId(repartidorId)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REPARTIDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Repartidor>> getRepById(@PathVariable Integer id) {
        Repartidor rep = repartidorService.getRepById(id);
        if (rep == null) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Repartidor no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Repartidor obtenido exitosamente", rep));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/curp/{curp}")
    public ResponseEntity<ApiResponse<Repartidor>> getRepByCurp(@PathVariable String curp) {
        Repartidor rep = repartidorService.getRepByCurp(curp);
        if (rep == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Repartidor no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Repartidor obtenido exitosamente", rep));

    }
    
    

}
