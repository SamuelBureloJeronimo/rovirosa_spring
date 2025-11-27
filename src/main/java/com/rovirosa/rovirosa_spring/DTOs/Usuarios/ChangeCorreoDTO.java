package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeCorreoDTO {
    
    @NotNull(message = "El id del usuario no puede ser nulo")
    private Integer id;
    @NotBlank(message = "El nuevo correo no puede ser nulo")
    private String nuevoCorreo;


}
