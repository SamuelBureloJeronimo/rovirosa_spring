package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoPostOrPutDTO;
import com.rovirosa.rovirosa_spring.DTOs.Carrito.CarritoQueryByClienteDTO;
import com.rovirosa.rovirosa_spring.models.Carrito;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.repositories.CarritoRepository;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;

    public void agregarAlCarrito(CarritoPostOrPutDTO carrito) {
        if(carrito.getId() == 0) {
            // Lógica para manejar el caso en que el ID es 0 (nuevo carrito)
            Cliente cliente = new Cliente();
            cliente.setId(carrito.getClienteId());

            Producto producto = new Producto();
            producto.setId(carrito.getProductoId());

            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setCliente(cliente);
            nuevoCarrito.setProducto(producto);
            nuevoCarrito.setCantidad(carrito.getCantidad());

            carritoRepository.save(nuevoCarrito);
        } else {
            // Lógica para manejar el caso en que el ID es diferente de 0 (carrito existente)
            Carrito carritoExistente = carritoRepository.findById(carrito.getId())
                    .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
            carritoExistente.setCantidad(carrito.getCantidad());
            carritoRepository.save(carritoExistente);
        }
    }

    public List<CarritoQueryByClienteDTO> getCarritoByUserId(Integer userId) {
        return carritoRepository.findByCliente_Usuario_Id(userId);
    }
}
