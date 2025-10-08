package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.EmpresaConfig;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.repositories.CategoriaRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;
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
    @Autowired
    private PuntoVentaRepository puntoRep;
    @Autowired
    private DireccionRepository direccionRep;
    @Autowired
    private StorageService storageService;

    @Override
    public Marca newBrand(Marca marca, MultipartFile logo) {
        marca.setLogo("marcas/"+storageService.generateFileName());
        Marca m = marcaRep.save(marca);
        if (m.getId() != null)
            storageService.store(logo, "", m.getLogo());
        return m;

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

    @Override
    public List<PuntoVenta> getPuntos() {
        return puntoRep.findAll();
    }

    @Override
    public PuntoVenta updatePunto(PuntoVenta punto) {
        return puntoRep.save(punto);
    }

    @Override
    public PuntoVenta getPunto(Integer id) {
        return puntoRep.findById(id).orElse(null);
    }

    @Override
    public Direccion getDireccion(Integer id) {
        return direccionRep.findById(id).orElse(null);
    }

    @Override
    public Direccion updateDireccion(Direccion direccion) {
        return direccionRep.save(direccion);
    }

    @Override
    public void deleteProduct(Integer id) {
        prodRep.findById(id).ifPresent(producto -> {
            // Eliminar la imagen del producto del almacenamiento
            storageService.delete(producto.getImagen());
            // Eliminar el producto de la base de datos
            prodRep.delete(producto);
        });
    }

    @Override
    public void updateProduct(Producto prod) {
        prodRep.save(prod);
    }

    @Override
    @Transactional
    public Boolean updateZone(Integer id, String zone) {
        return puntoRep.updateZona(id, zone) > 0;
    }
}
