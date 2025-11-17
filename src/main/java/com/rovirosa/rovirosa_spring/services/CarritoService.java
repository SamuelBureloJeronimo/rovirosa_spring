package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Descuento.DescuentoConfigQueryDTO;
import com.rovirosa.rovirosa_spring.models.Carrito;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.CarritoRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoCategRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoMarcaRepository;
import com.rovirosa.rovirosa_spring.repositories.DescuentoProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private DescuentoCategRepository descCategRepo;
    @Autowired
    private DescuentoMarcaRepository descMarcaRepo;
    @Autowired
    private DescuentoProductoRepository descProductoRepo;

    public CarritoQueryByClienteDTO createCarrito(CarritoPostDTO carrito) {

        // Lógica para manejar el caso en que el ID es 0 (nuevo carrito)
        Usuario usuario = new Usuario();
        usuario.setId(carrito.getUserId());

        CatalogoPv catalogo = new CatalogoPv();
        catalogo.setId(carrito.getCatalogoId());

        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuario(usuario);
        nuevoCarrito.setCatalogo(catalogo);
        nuevoCarrito.setCantidad(carrito.getCantidad());

        Carrito carritoGuardado = carritoRepository.save(nuevoCarrito);

        return carritoRepository.findProjectedById(carritoGuardado.getId());

    }

    public void updateCarrito(Integer id, Integer cantidad) {
        Carrito carritoExistente = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carritoExistente.setCantidad(cantidad);
        carritoRepository.save(carritoExistente);
    }

    @Transactional
    public boolean removeFromCart(Integer id, Integer catalogoId) {
        return carritoRepository.deleteByUsuarioIdAndCatalogoId(id, catalogoId) > 0;
    }

    public List<CarritoResponseDTO> getCarritoByUserId(Integer userId) {

        List<CarritoQueryByClienteDTO> carritoQuery = carritoRepository.findByUsuario_Id(userId);
        List<CarritoResponseDTO> res = new ArrayList<>();

        for (CarritoQueryByClienteDTO item : carritoQuery) {
            // Verificar descuento por producto
            DescuentoConfigQueryDTO descProd = descProductoRepo.findByProducto_Id(item.getCatalogo_Producto_Id());
            if (descProd != null) {
                CarritoResponseDTO response = new CarritoResponseDTO(item, descProd.getConfig_Valor(),
                        descProd.getConfig_Tipo());
                res.add(response);
                continue;
            }

            // Verificar descuento por marca
            DescuentoConfigQueryDTO descMarca = descMarcaRepo.findByMarca_Id(item.getCatalogo_Producto_Marca().getId());
            if (descMarca != null) {
                CarritoResponseDTO response = new CarritoResponseDTO(item, descMarca.getConfig_Valor(),
                        descMarca.getConfig_Tipo());
                res.add(response);
                continue;
            }

            // Verificar descuento por categoría
            DescuentoConfigQueryDTO descCateg = descCategRepo
                    .findByCategoria_Id(item.getCatalogo_Producto_Marca().getCategoria().getId());
            if (descCateg != null) {
                CarritoResponseDTO response = new CarritoResponseDTO(item, descCateg.getConfig_Valor(),
                        descCateg.getConfig_Tipo());
                res.add(response);
                continue;
            }
            CarritoResponseDTO response = new CarritoResponseDTO(item, null, null);
            res.add(response);
        }

        return res;
    }

    public void clearCartByUserId(Integer userId) {
        carritoRepository.deleteByUsuario_Id(userId);
    }
}
