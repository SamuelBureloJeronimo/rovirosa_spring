package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoConfigQueryDTO;
import com.rovirosa.rovirosa_spring.repositories.CatalogoPvRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoCategRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoMarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoProductoRepository;

@Service
public class CatalogoPvService {

    @Autowired
    private CatalogoPvRepository catalogoPvRep;
    @Autowired
    private DescuentoCategRepository descCategRepo;
    @Autowired
    private DescuentoMarcaRepository descMarcaRepo;
    @Autowired
    private DescuentoProductoRepository descProductoRepo;

    public List<CatalogoResponseDTO> getCatalogoByPuntoVentaIdAndMarcaId(Integer pv_id, Integer marca_id) {

        List<CatalogoQueryDTO> catalogo = catalogoPvRep.findByPuntoVenta_IdAndProducto_Marca_Id(pv_id, marca_id);
        List<CatalogoResponseDTO> res = new ArrayList<>();
        
        for (CatalogoQueryDTO item : catalogo) {
            DescuentoConfigQueryDTO descProd = descProductoRepo.findByProducto_Id(item.getId());
            if(descProd != null){
                CatalogoResponseDTO response = new CatalogoResponseDTO(item, descProd.getConfig_Valor(), descProd.getConfig_Tipo());
                res.add(response);
                continue;
            }
            DescuentoConfigQueryDTO descMarca = descMarcaRepo.findByMarca_Id(item.getProducto_Marca().getId());
            if(descMarca != null){
                CatalogoResponseDTO response = new CatalogoResponseDTO(item, descMarca.getConfig_Valor(), descMarca.getConfig_Tipo());
                res.add(response);
                continue;
            }
            DescuentoConfigQueryDTO descCateg = descCategRepo.findByCategoria_Id(item.getProducto_Marca().getCategoria().getId());
            if(descCateg != null){
                CatalogoResponseDTO response = new CatalogoResponseDTO(item, descCateg.getConfig_Valor(), descCateg.getConfig_Tipo());
                res.add(response);
                continue;
            }
            CatalogoResponseDTO response = new CatalogoResponseDTO(item, null, null);
            res.add(response);
        }
        return res;
    }
    
}
