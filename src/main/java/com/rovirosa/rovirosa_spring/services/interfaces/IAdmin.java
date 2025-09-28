package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;

public interface IAdmin {

    public List<Cliente> getAll();
    public Marca newBrand(Marca marca);
    public Categoria newCateg(Categoria categ);
    public Producto newProducto(Producto categ);

}
