package com.rovirosa.rovirosa_spring.DTOs.ChatVenta;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatVentaPostDTO {
    
    @NotNull(message = "El ID de la venta no puede ser nulo")
    private Integer ventaId;
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer userId;
    private String mensaje;
    private String archivo;

}
