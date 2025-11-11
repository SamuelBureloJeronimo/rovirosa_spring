package com.rovirosa.rovirosa_spring.DTOs.Producto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDimencionesDTO {
    
    private BigDecimal pesoKg;
    private BigDecimal volM3;
}
