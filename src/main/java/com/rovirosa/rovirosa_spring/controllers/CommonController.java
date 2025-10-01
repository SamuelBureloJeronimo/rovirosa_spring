package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.AppInfoDTO;
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

    @GetMapping("/get-products")
    public List<Producto> getProductos() {
        return commonServ.getProductos();
    }

    @GetMapping("/get-categs")
    public List<Categoria> getCategs() {
        return commonServ.getCategorias();
    }

}
