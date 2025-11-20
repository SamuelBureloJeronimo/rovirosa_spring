package com.rovirosa.rovirosa_spring.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.HorarioLaboral.HorarioLaboralPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaDetallesDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaResponseDTO;
import com.rovirosa.rovirosa_spring.services.PuntoVentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/punto-venta")
public class PuntoVentaController {

    @Autowired
    private PuntoVentaService puntoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detalles/{id}")
    public ResponseEntity<ApiResponse<PuntoVentaDetallesDTO>> getPuntoDetalles(@PathVariable Integer id) {
        PuntoVentaDetallesDTO dto = puntoService.getPuntoDetalles(id);
        if (dto == null) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, "No se encontró el punto de venta.", null)
            );
        }
        return ResponseEntity.status(200).body(
            new ApiResponse<>(true, "Punto de venta obtenido exitosamente.", dto)
        );
    }

     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping
        public ResponseEntity<ApiResponse<PuntoVentaResponseDTO>> createPunto(
                @Valid @RequestPart("dto") PuntoVentaPostDTO dto, 
                @Valid @RequestPart("horarios") List<DiaLaboralPostDTO> horarios) {

            System.out.println("RFC: " + dto.getRfc());
            System.out.println("Nombre: " + dto.getNombre());
            System.out.println("Latitud: " + dto.getLat());
            System.out.println("Longitud: " + dto.getLng());
            System.out.println("Zona: " + dto.getZona());

            System.out.println("Horarios:");

            for (DiaLaboralPostDTO dia : horarios) {
                System.out.println("Día de la semana: " + dia.getDiaSemana());
                for (HorarioLaboralPostDTO horario : dia.getHorarios()) {
                    System.out.println("Hora de apertura: " + horario.gethApertura().toString());
                    System.out.println("Hora de cierre: " + horario.gethCierre().toString());
                }
            }

            PuntoVentaResponseDTO nuevo = puntoService.createPunto(dto, horarios);

            return ResponseEntity.status(201).body(
                    new ApiResponse<PuntoVentaResponseDTO>(true, "Punto de venta creado exitosamente.", nuevo));
        }


    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PuntoVentaQueryDTO>>> getPuntos() {
        List<PuntoVentaQueryDTO> puntos = puntoService.getAllPuntos();
        return ResponseEntity.status(200).body(
                new ApiResponse<List<PuntoVentaQueryDTO>>(true, "Puntos de venta obtenidos exitosamente.", puntos));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE','REPARTIDOR')")
    @GetMapping("/{pvId}")
    public ResponseEntity<ApiResponse<PuntoVentaQueryDTO>> getById(@PathVariable Integer pvId) {
        PuntoVentaQueryDTO pv = puntoService.getDireccion(pvId);
        if (pv == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "No se encontró el punto de venta.", null));
        }
        return ResponseEntity.status(200).body(
                new ApiResponse<>(true, "Punto de venta obtenida exitosamente.", pv));
    }
    

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/avalible-zone")
    public ResponseEntity<HashMap<String, String>>
            updatePunto(@RequestParam Integer id, @RequestParam String zone) {
                
        HashMap<String, String> response = new HashMap<>();
        Boolean rowAffected = puntoService.updateZone(id, zone);

        if (!rowAffected)
            return ResponseEntity.status(404).body(response);

        response.put("success", "Zona actualizada exitosamente.");
        return ResponseEntity.status(200).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePunto(@PathVariable Integer id) {
        boolean deleted = puntoService.deletePunto(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Punto de venta eliminado exitosamente", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Punto de venta no encontrado", null));
        }  
    }

}
