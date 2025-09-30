package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IAdmin;

@Service
public class AdminService extends CommonService implements IAdmin {

    @Autowired
    private MarcaRepository marcaRep;
    @Autowired
    private CategoriaRepository categRep;
    @Autowired
    private ProductoRepository prodRep;
    @Autowired
    private EmpresaConfigRepository configRep;

    @Override
    public Marca newBrand(Marca marca) {
        return marcaRep.save(marca);
    }

    @Override
    public Categoria newCateg(Categoria categ) {
        return categRep.save(categ);
    }

    @Override
    public Producto newProducto(Producto prod) {
        return prodRep.save(prod);
    }

    @Override
    public EmpresaConfig getConfig() {
        return configRep.findAll().stream().findFirst().orElse(null);
    }

    @Override
    public EmpresaConfig updateConfig(EmpresaConfig config) {
        return configRep.save(config);
    }
}
