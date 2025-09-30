package com.rovirosa.rovirosa_spring.controllers;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.services.AdminService;
import com.rovirosa.rovirosa_spring.services.StorageService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends CommonController {

    @Autowired
    @Qualifier("adminService")
    private AdminService adminServ;
    @Autowired
    private StorageService storageService;

    @GetMapping("/get-config")
    public ResponseEntity<HashMap<String,EmpresaConfig>>  getConfig() {
        HashMap<String,EmpresaConfig> response = new HashMap<>();
        EmpresaConfig config = adminServ.getConfig();
        if(config == null)
            return ResponseEntity.status(404).body(response);
        response.put("success", config);
        return ResponseEntity.status(200).body(response);
    }
    


    @PostMapping("/new-product")
    public ResponseEntity<HashMap<String,Producto>> newProduct(@RequestPart Producto producto, @RequestPart("imagen") MultipartFile imagen) {
        HashMap<String,Producto> response = new HashMap<>();
        producto.setImagen("productos/"+storageService.store(imagen,"productos/"));
        producto = adminServ.newProducto(producto);
        response.put("success", producto);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/new-brand")
    public ResponseEntity<HashMap<String,Marca>> newBrand(@RequestParam String nombre, @RequestParam Integer categ_id, @RequestPart("logo") MultipartFile logo) {
        HashMap<String,Marca> response = new HashMap<>();
        Marca marca = new Marca();
        marca.setNombre(nombre);
        Categoria categ = new Categoria();
        categ.setId(categ_id);
        marca.setCategoria(categ);
        marca.setLogo("marcas/"+storageService.store(logo,"marcas/"));
        Marca marcaReg = adminServ.newBrand(marca);
        response.put("success", marcaReg);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/new-categ")
    public ResponseEntity<HashMap<String,Categoria>> newCateg(@RequestParam String nombre) {
        HashMap<String,Categoria> response = new HashMap<>();
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria = adminServ.newCateg(categoria);
        response.put("success", categoria);
        return ResponseEntity.status(200).body(response);
    }
    
}
