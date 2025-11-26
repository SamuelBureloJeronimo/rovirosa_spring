package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CarritoResponseDTO {
    
    private Integer id;
    private Integer cantidad;
    private LocalDateTime created;
    private LocalDateTime updated;

    // Datos del catálogo
    private Integer catalogo_Id;
    private Integer catalogo_Producto_Id;
    private String catalogo_Producto_Nombre;
    private Double catalogo_Producto_Precio;
    private String catalogo_Producto_Imagen;
    private Integer catalogo_PuntoVenta_Id;
    private String catalogo_PuntoVenta_Nombre;
    private Double catalogo_Producto_PesoKg;
    private BigDecimal catalogo_Producto_VolM3;
    private Integer catalogo_Stock;

    // Valores de promociones aplicadas
    private Double valor;
    private String tipo;

    public CarritoResponseDTO(CarritoQueryByClienteDTO carritoQuery, Double valor, String tipo) {
        this.id = carritoQuery.getId();
        this.cantidad = carritoQuery.getCantidad();
        this.created = carritoQuery.getCreated();
        this.updated = carritoQuery.getUpdated();
        this.catalogo_Id = carritoQuery.getCatalogo_Id();
        this.catalogo_Producto_Id = carritoQuery.getCatalogo_Producto_Id();
        this.catalogo_Producto_Nombre = carritoQuery.getCatalogo_Producto_Nombre();
        this.catalogo_Producto_Precio = carritoQuery.getCatalogo_Producto_Precio();
        this.catalogo_Producto_Imagen = carritoQuery.getCatalogo_Producto_Imagen();
        this.catalogo_Producto_PesoKg = carritoQuery.getCatalogo_Producto_PesoKg();
        this.catalogo_Producto_VolM3 = carritoQuery.getCatalogo_Producto_VolM3();
        this.catalogo_PuntoVenta_Id = carritoQuery.getCatalogo_PuntoVenta_Id();
        this.catalogo_PuntoVenta_Nombre = carritoQuery.getCatalogo_PuntoVenta_Nombre();
        this.catalogo_Stock = carritoQuery.getCatalogo_Stock();
        this.valor = valor;
        this.tipo = tipo;
    }

}
