package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoResponseDTO;
import com.rovirosa.rovirosa_spring.services.CatalogoPvService;


@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

        @Autowired
        private CatalogoPvService catalogoService;

        @PreAuthorize("hasAnyRole('ADMIN')")
        @PostMapping
        public ResponseEntity<ApiResponse<CatalogoQueryDTO>> 
        addProducto(@RequestBody CatalogoPostDTO dto) {

                if(catalogoService.existPvAndProd(dto.getPuntoVenta_id(), dto.getProducto_id())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                        new ApiResponse<CatalogoQueryDTO>(false,
                                                        "El producto ya existe en el catálogo del punto de venta.", null));
                }

                CatalogoQueryDTO response = catalogoService.addProducto(dto);
                if (response == null) {
                        return ResponseEntity.status(400).body(
                                        new ApiResponse<CatalogoQueryDTO>(false,
                                                        "Error al agregar producto.", null));
                }
                return ResponseEntity.status(201).body(
                                new ApiResponse<CatalogoQueryDTO>(true, "Producto agregado exitosamente.",
                                                response));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/update-stock")
        public ResponseEntity<ApiResponse<CatalogoQueryDTO>> updateStock(@RequestBody CatalogoPostDTO dto) {
                CatalogoQueryDTO response = catalogoService.updateStock(dto);
                if (response == null) {
                        return ResponseEntity.status(400).body(
                                        new ApiResponse<CatalogoQueryDTO>(false,
                                                        "Error al actualizar el stock.", null));
                }
                return ResponseEntity.status(200).body(
                                new ApiResponse<CatalogoQueryDTO>(true, "Stock actualizado exitosamente.",
                                                response));
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @DeleteMapping("/{pv_id}/{prod_id}")
        public ResponseEntity<ApiResponse<String>> removeProducto(@PathVariable Integer pv_id, @PathVariable Integer prod_id) {

                // Lógica para eliminar el producto del catálogo
                if (!catalogoService.existPvAndProd(pv_id, prod_id)) {
                        return ResponseEntity.status(404).body(
                                        new ApiResponse<String>(false,
                                                        "El producto no existe en el catálogo del punto de venta.", null));
                }

                Integer removed = catalogoService.removeProducto(pv_id, prod_id);

                if (removed == 0) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                        new ApiResponse<String>(false, "Error al eliminar el producto.", null));
                }

                // Suponiendo que la eliminación fue exitosa
                return ResponseEntity.status(200).body(
                                new ApiResponse<String>(true, "Producto eliminado exitosamente.", null));
        }

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
