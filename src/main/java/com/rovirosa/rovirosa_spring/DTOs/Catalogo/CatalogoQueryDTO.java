package com.rovirosa.rovirosa_spring.DTOs.Catalogo;

import com.rovirosa.rovirosa_spring.models.Marca;

public interface CatalogoQueryDTO {

    public Integer getId();
    public Integer getStock();
    public Integer getVendidos();
    public String getProducto_Nombre();
    public String getProducto_Imagen();
    public Double getProducto_Precio();

    public Marca getProducto_Marca();
    
}
