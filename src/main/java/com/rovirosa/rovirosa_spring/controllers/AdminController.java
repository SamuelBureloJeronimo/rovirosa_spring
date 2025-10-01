package com.rovirosa.rovirosa_spring.controllers;

import java.util.HashMap;
import java.util.List;

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
import com.rovirosa.rovirosa_spring.models.PuntoDistribucion;
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
    public ResponseEntity<HashMap<String, EmpresaConfig>> getConfig() {
        HashMap<String, EmpresaConfig> response = new HashMap<>();
        EmpresaConfig config = adminServ.getConfig();
        if (config == null)
            return ResponseEntity.status(404).body(response);
        response.put("success", config);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/update-config")
    public ResponseEntity<HashMap<String, EmpresaConfig>> updateConfig(
            @RequestParam String rfc, @RequestParam String nom, @RequestParam Integer montoMin,
            @RequestParam String descrip, @RequestPart(name = "logo", required = false) MultipartFile logo) {
        HashMap<String, EmpresaConfig> response = new HashMap<>();
        EmpresaConfig config = adminServ.getConfig();
        config.setRfc(rfc);
        config.setNombre(nom);
        config.setMontoMin(montoMin);
        config.setDescrip(descrip);
        config = adminServ.updateConfig(config);
        if (logo != null && !logo.isEmpty())
            storageService.store(logo, "", logo.getOriginalFilename());
        response.put("success", config);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/update-mail")
    public ResponseEntity<HashMap<String, String>> updateGmail(@RequestParam String correo, @RequestParam String pass) {
        HashMap<String, String> response = new HashMap<>();
        EmpresaConfig config = adminServ.getConfig();
        config.setEmailApp(correo);
        config.setCodigoApp(pass);
        config = adminServ.updateConfig(config);
        if (config == null)
            return ResponseEntity.status(404).body(response);
        response.put("success", "Configuración de correo actualizada.");
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/get-mail")
    public HashMap<String, String> getMailConfig() {
        HashMap<String, String> response = new HashMap<>();
        EmpresaConfig config = adminServ.getConfig();
        if (config == null) {
            response.put("error", "No se encontró la configuración de correo.");
            return response;
        }
        response.put("correo", config.getEmailApp());
        response.put("pass", config.getCodigoApp());
        return response;
    }

    @GetMapping("/get-puntos")
    public List<PuntoDistribucion> getPuntos() {
        return adminServ.getPuntos();
    }
    
    

    @PostMapping("/new-product")
    public ResponseEntity<HashMap<String, Producto>> newProduct(@RequestPart Producto producto,
            @RequestPart("imagen") MultipartFile imagen) {
        HashMap<String, Producto> response = new HashMap<>();
        producto.setImagen("productos/" + storageService.store(imagen, "productos/"));
        producto = adminServ.newProducto(producto);
        response.put("success", producto);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/new-brand")
    public ResponseEntity<HashMap<String, Marca>> newBrand(@RequestParam String nombre, @RequestParam Integer categ_id,
            @RequestPart("logo") MultipartFile logo) {
        HashMap<String, Marca> response = new HashMap<>();
        Marca marca = new Marca();
        marca.setNombre(nombre);
        Categoria categ = new Categoria();
        categ.setId(categ_id);
        marca.setCategoria(categ);
        marca.setLogo("marcas/" + storageService.store(logo, "marcas/"));
        Marca marcaReg = adminServ.newBrand(marca);
        response.put("success", marcaReg);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/new-categ")
    public ResponseEntity<HashMap<String, Categoria>> newCateg(@RequestParam String nombre) {
        HashMap<String, Categoria> response = new HashMap<>();
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria = adminServ.newCateg(categoria);
        response.put("success", categoria);
        return ResponseEntity.status(200).body(response);
    }

}
