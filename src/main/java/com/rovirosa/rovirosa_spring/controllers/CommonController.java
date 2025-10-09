package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.AppInfoDTO;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.services.CommonService;

@RestController
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    @Qualifier("commonService")
    private CommonService commonServ;

    @GetMapping("/get-info")
    public AppInfoDTO getInfoApp() {
        return commonServ.getInfoApp();
    }

    @GetMapping("/get-brands")
    public List<Marca> getBrands() {
        return commonServ.getBrands();
    }

    @GetMapping("/get-brands-by-categ/{id}")
    public List<Marca> getBrandsByCategory(@PathVariable Integer id) {
        return commonServ.getBrandsByCategory(id);
    }

    @GetMapping("/get-products")
    public List<Producto> getProductos() {
        return commonServ.getProductos();
    }

    @GetMapping("/get-catalogo-pv/{id}")
    public List<CatalogoPv> getCatalogoPv(@PathVariable Integer id) {
        return commonServ.getCatalogoPv(id);
    }

    @GetMapping("/get-product/{id}")
    public Producto getProducto(@PathVariable Integer id) {
        return commonServ.getProducto(id);
    }

    @GetMapping("/get-categs")
    public List<Categoria> getCategs() {
        return commonServ.getCategorias();
    }

}
