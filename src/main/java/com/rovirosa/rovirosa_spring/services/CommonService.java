package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.AppInfoDTO;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.ICommon;

@Service
public class CommonService implements ICommon {

    @Autowired
    private MarcaRepository marcaRep;
    @Autowired
    private CategoriaRepository categRep;
    @Autowired
    private ProductoRepository prodRep;
    @Autowired
    private EmpresaConfigRepository configRep; 

    @Override
    public List<Marca> getBrands() {
        return this.marcaRep.findAll();
    }

    @Override
    public List<Categoria> getCategorias() {
        return this.categRep.findAll();
    }

    @Override
    public List<Producto> getProductos() {
        return prodRep.findAll();
    }

    @Override
    public AppInfoDTO getInfoApp() {
        EmpresaConfig config = configRep.findAll().getFirst();
        String appName = config.getNombre();
        String logo = config.getLogo();
        return new AppInfoDTO(appName, logo);
    }

    @Override
    public Producto getProducto(Integer id) {
        return prodRep.findById(id).orElse(null);
    }
}
