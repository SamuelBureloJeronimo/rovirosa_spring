package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePassDTO {

    @NotNull(message = "El id del usuario no puede ser nulo")
    private Integer id;
    @NotBlank(message = "La contraseña actual no puede ser nulo")
    private String actualPass;
    @NotBlank(message = "La contraseña no puede ser nulo")
    private String newPass;
    
}
