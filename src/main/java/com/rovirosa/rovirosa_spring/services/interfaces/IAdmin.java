package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;

public interface IAdmin {

    public Marca newBrand(Marca marca);
    public EmpresaConfig getConfig();
    public Direccion getDireccion(Integer id);
    public Direccion updateDireccion(Direccion direccion);
    public EmpresaConfig updateConfig(EmpresaConfig config);
    public PuntoVenta updatePunto(PuntoVenta punto);
    public Categoria newCateg(Categoria categ);
    public Producto newProducto(Producto categ);
    public List<PuntoVenta> getPuntos();
    public void deleteProduct(Integer id);
    public PuntoVenta getPunto(Integer id);
    public void updateProduct(Producto prod);
    public Boolean updateZone(Integer id, String zone);

}
