package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.models.Carrito;
import com.rovirosa.rovirosa_spring.models.CatalogoPv;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.CarritoRepository;

import jakarta.transaction.Transactional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

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

    public List<CarritoQueryByClienteDTO> getCarritoByUserId(Integer userId) {
        return carritoRepository.findByUsuario_Id(userId);
    }
}
