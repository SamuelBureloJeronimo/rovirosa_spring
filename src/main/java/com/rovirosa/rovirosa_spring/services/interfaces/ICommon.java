package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.AppInfoDTO;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;

public interface ICommon {
    public List<Marca> getBrands();
    public AppInfoDTO getInfoApp();
    public List<Categoria> getCategorias();
    public List<Producto> getProductos();
    public Producto getProducto(Integer id);
}
