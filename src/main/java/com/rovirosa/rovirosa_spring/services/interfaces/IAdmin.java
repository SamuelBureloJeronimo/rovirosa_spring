package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;

public interface IAdmin {

    public Marca newBrand(Marca marca);
    public EmpresaConfig getConfig();
    public EmpresaConfig updateConfig(EmpresaConfig config);
    public Categoria newCateg(Categoria categ);
    public Producto newProducto(Producto categ);

}
