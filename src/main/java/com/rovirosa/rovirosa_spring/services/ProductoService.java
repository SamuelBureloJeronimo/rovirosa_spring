package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoCreateDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoDeleteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoUpdateDTO;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private MarcaRepository marcaRepo;

    @Autowired
    private StorageService storageService;

    public Producto getProductById(Integer id) {
        return productoRepo.findById(id).orElse(null);
    }

    // Crear producto
    public ProductoResponseDTO create(ProductoCreateDTO dto, MultipartFile imagen) {
        Marca marca = marcaRepo.findById(dto.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setMarca(marca);
        p.setPrecio(dto.getPrecio());
        p.setPesoKg(dto.getPesoKg());
        p.setVolM3(dto.getVolM3());
        p.setImagen("productos/" + storageService.store(imagen, "productos/"));

        productoRepo.save(p);
        return mapToResponse(p);
    }

    // Actualizar producto
    public ProductoResponseDTO update(ProductoUpdateDTO dto, MultipartFile imagen) {
        Producto p = productoRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (dto.getNombre() != null)
            p.setNombre(dto.getNombre());
        if (dto.getPrecio() != null)
            p.setPrecio(dto.getPrecio());
        if (dto.getPesoKg() != null)
            p.setPesoKg(dto.getPesoKg());
        if (dto.getVolM3() != null)
            p.setVolM3(dto.getVolM3());

        if (dto.getMarcaId() != null) {
            Marca marca = marcaRepo.findById(dto.getMarcaId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            p.setMarca(marca);
        }

        if (imagen != null && !imagen.isEmpty()) {
            p.setImagen("productos/" + storageService.store(imagen, "productos/"));
        }

        productoRepo.save(p);
        return mapToResponse(p);
    }

    public boolean delete(Integer id) {

        if (productoRepo.existsById(id)) {
            
            ProductoDeleteDTO producto = productoRepo.findProductoDeleteDTOById(id);
            
            if (producto != null)
                storageService.delete(producto.getImagen());
            
            productoRepo.deleteById(id);
            return true; // eliminado correctamente
        } else {
            return false; // no existía
        }
    }

    private ProductoResponseDTO mapToResponse(Producto p) {
        ProductoResponseDTO res = new ProductoResponseDTO();
        res.setId(p.getId());
        res.setNombre(p.getNombre());
        res.setPrecio(p.getPrecio());
        res.setImagen(p.getImagen());
        res.setPesoKg(p.getPesoKg());
        res.setVolM3(p.getVolM3());
        res.setMarcaNombre(p.getMarca().getNombre());
        res.setMarcaLogo(p.getMarca().getLogo());
        return res;
    }
}
