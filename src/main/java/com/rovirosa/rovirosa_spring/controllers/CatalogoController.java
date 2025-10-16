package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoResponseDTO;
import com.rovirosa.rovirosa_spring.services.CatalogoPvService;


@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

        @Autowired
        private CatalogoPvService catalogoService;

        @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
        @GetMapping("/{marca_id}/{pv_id}")
        public ResponseEntity<ApiResponse<List<CatalogoResponseDTO>>> getCatalogoByPuntoVentaId(
                        @PathVariable Integer marca_id, @PathVariable Integer pv_id) {

                System.out.println("Marca ID: " + marca_id + ", Punto de Venta ID: " + pv_id);

                List<CatalogoResponseDTO> catalogo = catalogoService.getCatalogoByPuntoVentaIdAndMarcaId(pv_id, marca_id);
                if (catalogo == null) {
                        return ResponseEntity.status(404).body(
                                        new ApiResponse<List<CatalogoResponseDTO>>(false,
                                                        "No se encontraron productos.", null));
                }

                return ResponseEntity.status(200).body(
                                new ApiResponse<List<CatalogoResponseDTO>>(true, "Productos obtenidos exitosamente.",
                                                catalogo));
        }
        

}
