package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;

import jakarta.transaction.Transactional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRep;
    @Autowired
    private ProductoService productoServ;
    @Autowired
    private StorageService storageService;

    public Marca newBrand(Marca marca, MultipartFile logo) {
        marca.setLogo("marcas/" + storageService.generateFileName());
        Marca m = marcaRep.save(marca);
        if (m.getId() != null)
            storageService.store(logo, "", m.getLogo());
        return m;
    }

    public List<Marca> getMarcas() {
        return this.marcaRep.findAll();
    }

    @Transactional
    public Marca editarMarca(Marca marca, MultipartFile logo) {
        Marca existingMarca = marcaRep.findById(marca.getId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        existingMarca.setNombre(marca.getNombre());
        existingMarca.setCategoria(marca.getCategoria());

        if (logo != null && !logo.isEmpty()) {
            String oldLogoPath = existingMarca.getLogo();
            storageService.store(logo, "marcas/", oldLogoPath.replace("marcas/", ""));
        }

        return marcaRep.save(existingMarca);
    }

    @Transactional
    public void deleteById(Integer id) {
        Marca marca = marcaRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        String logoPath = marca.getLogo();
        storageService.delete(logoPath);

        List<Producto> productos = productoServ.getAllProducts();
        for (Producto producto : productos) {
            if (producto.getMarca().getId().equals(id)) {
                productoServ.delete(producto.getId());
            }
        }

        marcaRep.deleteById(id);
    }

}
