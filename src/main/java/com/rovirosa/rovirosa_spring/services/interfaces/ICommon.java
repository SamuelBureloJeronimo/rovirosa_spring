package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.CatalogoPv;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;

public interface ICommon {
    public List<Marca> getBrands();
    public List<Marca> getBrandsByCategory(Integer id);
    public List<Categoria> getCategorias();
    public List<Producto> getProductos();
    public List<CatalogoPv> getCatalogoPv(Integer id);
    public Producto getProducto(Integer id);
}
