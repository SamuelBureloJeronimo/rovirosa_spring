package com.rovirosa.rovirosa_spring.DTOs.Descuento;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.DescuentoCategoria;
import com.rovirosa.rovirosa_spring.models.DescuentoMarca;
import com.rovirosa.rovirosa_spring.models.DescuentoProducto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocionesDTO {
    
    private List<DescuentoProducto> descuentosProducto;
    private List<DescuentoMarca> descuentosMarca;
    private List<DescuentoCategoria> descuentosCategoria;

}
