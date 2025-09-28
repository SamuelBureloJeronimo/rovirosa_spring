package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IAdmin;

@Service
public class AdminService extends CommonService implements IAdmin {
    @Autowired
    private ClienteRepository clienteRep;
    @Autowired
    private MarcaRepository marcaRep;
    @Autowired
    private CategoriaRepository categRep;
    @Autowired
    private ProductoRepository prodRep;

    @Override
    public List<Cliente> getAll() {
        return clienteRep.findAll();
    }

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
}
