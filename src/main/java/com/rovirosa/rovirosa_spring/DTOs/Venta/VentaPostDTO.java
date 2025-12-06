package com.rovirosa.rovirosa_spring.DTOs.Venta;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaDTO;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Pago.PagoDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaPostDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer userId;
    
    @NotNull(message = "El ID del punto de venta es obligatorio")
    private Integer pvId;

    @NotNull(message = "Los datos de pago son obligatorios")
    private PagoDTO pago;

    @NotNull(message = "Los detalles de la venta son obligatorios")
    private List<DetalleVentaDTO> detallesVenta;

    @NotNull(message = "La dirección de envío es obligatoria")
    private DireccionPostDTO direccion;

}