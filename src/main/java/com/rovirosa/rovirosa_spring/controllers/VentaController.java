package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaResponseClienteDTO;
import com.rovirosa.rovirosa_spring.services.VentaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> crearVenta(
        @Valid @RequestPart("venta") VentaPostDTO ventaDTO,
        @RequestPart(value = "comprobante", required = false) MultipartFile comprobante
    ) {

        System.out.println("\nCreando venta con los siguientes datos:");
        System.out.println("Cliente ID: " + ventaDTO.getClienteId());
        System.out.println("PV ID: " + ventaDTO.getPvId());
        System.out.println("\nDetallesVenta: ");

        for (var detalle : ventaDTO.getDetallesVenta()) {
            System.out.println(" - Producto ID: " + detalle.getProductoId() + ", Cantidad: " + detalle.getCantidad() + ", Precio Unitario: " + detalle.getPrecioUnitario() + ", Descuento Unitario: " + detalle.getDescuentoUnitario());
        }

        System.out.println("\nPago Monto: " + ventaDTO.getPago().getMonto());
        System.out.println("Pago Metodo: " + ventaDTO.getPago().getMetodo());

        System.out.println("\nDireccion Lat: " + ventaDTO.getDireccion().getLat());
        System.out.println("Direccion Lng: " + ventaDTO.getDireccion().getLng());
        System.out.println("Direccion Ref: " + ventaDTO.getDireccion().getRef());
        System.out.println("\n");
        System.out.println("Comprobante: " + (comprobante != null ? comprobante.getOriginalFilename() : "No proporcionado"));

        ventaService.crearVenta(ventaDTO, comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Venta creada exitosamente", null));
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ApiResponse<List<VentaResponseClienteDTO>>> getVentasByClienteId(
        @PathVariable Integer clienteId
    ) {
        List<VentaResponseClienteDTO> ventas = ventaService.getVentasByClienteId(clienteId);
        
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Compras obtenidas exitosamente", ventas));
    }
    

}
