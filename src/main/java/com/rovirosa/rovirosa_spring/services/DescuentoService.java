package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.Descuento.BannerQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.PromocionesDTO;
import com.rovirosa.rovirosa_spring.models.Categoria;
import com.rovirosa.rovirosa_spring.models.DescuentoCategoria;
import com.rovirosa.rovirosa_spring.models.DescuentoConfig;
import com.rovirosa.rovirosa_spring.models.DescuentoMarca;
import com.rovirosa.rovirosa_spring.models.DescuentoProducto;
import com.rovirosa.rovirosa_spring.models.Marca;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.DescuentoCategoriaRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoConfigRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoMarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoProductoRepository;
import com.rovirosa.rovirosa_spring.repositories.MarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class DescuentoService {

    @Autowired
    private DescuentoConfigRepository desConfigRep;
    @Autowired
    private DescuentoCategoriaRepository descCategRep;
    @Autowired
    private DescuentoMarcaRepository descMarcaRep;
    @Autowired
    private DescuentoProductoRepository descuentoProductoRep;
    @Autowired
    private ProductoRepository productoRep;

    @Autowired
    private MarcaRepository marcaRep;

    @Autowired
    private StorageService storageService;

    public PromocionesDTO getDescuentos() {
        List<DescuentoProducto> descuentosProducto = descuentoProductoRep.findAll();
        List<DescuentoMarca> descuentosMarca = descMarcaRep.findAll();
        List<DescuentoCategoria> descuentosCategoria = descCategRep.findAll();
        PromocionesDTO promocionesDTO = new PromocionesDTO();
        promocionesDTO.setDescuentosProducto(descuentosProducto);
        promocionesDTO.setDescuentosMarca(descuentosMarca);
        promocionesDTO.setDescuentosCategoria(descuentosCategoria);
        return promocionesDTO;
    }

    public List<BannerQueryDTO> getAllBanners() {
        return desConfigRep.findAllBanners();
    }

    @Transactional
    public Boolean aplicarToProducto(DescuentoPostDTO dto, MultipartFile banner) {
        DescuentoConfig config = getConfigFromDTO(dto);

        String prefix = "descuentos/";
        String fileName = storageService.generateFileName();

        if (banner != null && !banner.isEmpty())
            config.setBanner(prefix + fileName);

        config = desConfigRep.save(config);

        Producto producto = productoRep.findById(dto.getId()).orElse(null);
        if (producto == null)
            return false;

        DescuentoProducto descuentoProducto = new DescuentoProducto();
        descuentoProducto.setConfig(config);
        descuentoProducto.setProducto(producto);

        descuentoProductoRep.save(descuentoProducto);

        if (banner != null && !banner.isEmpty())
            storageService.store(banner, prefix, fileName);

        return true;

    }

    @Transactional
    public void aplicarToMarca(DescuentoPostDTO dto, MultipartFile banner) {
        DescuentoConfig config = getConfigFromDTO(dto);

        System.out.println(dto.getId());
        System.out.println(dto.getTipo());
        System.out.println(dto.getValor());
        System.out.println(dto.getObjetivo());
        System.out.println(dto.getFechaIn());
        System.out.println(dto.getFechaFin());

        String prefix = "descuentos/";
        String fileName = storageService.generateFileName();

        if (banner != null && !banner.isEmpty())
            config.setBanner(prefix + fileName);

        config = desConfigRep.save(config);

        Marca marca = marcaRep.getReferenceById(dto.getId());

        DescuentoMarca descuentoMarca = new DescuentoMarca();
        descuentoMarca.setConfig(config);
        descuentoMarca.setMarca(marca);

        descMarcaRep.save(descuentoMarca);

        if (banner != null && !banner.isEmpty())
            storageService.store(banner, prefix, fileName);
    }

    @Transactional
    public void aplicarToCategoria(DescuentoPostDTO dto, MultipartFile banner) {
        DescuentoConfig config = getConfigFromDTO(dto);

        String prefix = "descuentos/";
        String fileName = storageService.generateFileName();

        if (banner != null && !banner.isEmpty())
            config.setBanner(prefix + fileName);

        config = desConfigRep.save(config);

        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());

        DescuentoCategoria descuentoCategoria = new DescuentoCategoria();
        descuentoCategoria.setConfig(config);
        descuentoCategoria.setCategoria(categoria);

        descCategRep.save(descuentoCategoria);

        if (banner != null && !banner.isEmpty())
            storageService.store(banner, prefix, fileName);
    }

    private DescuentoConfig getConfigFromDTO(DescuentoPostDTO dto) {
        DescuentoConfig config = new DescuentoConfig();
        config.setTipo(dto.getTipo());
        config.setObjetivo(dto.getObjetivo());
        config.setValor(dto.getValor());
        config.setFechaIn(dto.getFechaIn());
        config.setFechaFin(dto.getFechaFin());
        return config;
    }

    @Transactional
    public void eliminarDescuento(Integer idConfig) {
        DescuentoConfig config = desConfigRep.findById(idConfig)
                .orElseThrow(() -> new RuntimeException("DescuentoConfig not found with id: " + idConfig));
        if (config.getBanner() != null) {
            storageService.delete(config.getBanner());
        }
        desConfigRep.deleteById(idConfig);
    }

}
