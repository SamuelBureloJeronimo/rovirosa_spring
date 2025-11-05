package com.rovirosa.rovirosa_spring.DTOs.Carrito;

import java.time.LocalDateTime;

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
        this.catalogo_PuntoVenta_Id = carritoQuery.getCatalogo_PuntoVenta_Id();
        this.catalogo_PuntoVenta_Nombre = carritoQuery.getCatalogo_PuntoVenta_Nombre();
        this.catalogo_Stock = carritoQuery.getCatalogo_Stock();
        this.valor = valor;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Integer getCatalogo_Id() {
        return catalogo_Id;
    }

    public void setCatalogo_Id(Integer catalogo_Id) {
        this.catalogo_Id = catalogo_Id;
    }

    public Integer getCatalogo_Producto_Id() {
        return catalogo_Producto_Id;
    }

    public void setCatalogo_Producto_Id(Integer catalogo_Producto_Id) {
        this.catalogo_Producto_Id = catalogo_Producto_Id;
    }

    public String getCatalogo_Producto_Nombre() {
        return catalogo_Producto_Nombre;
    }

    public void setCatalogo_Producto_Nombre(String catalogo_Producto_Nombre) {
        this.catalogo_Producto_Nombre = catalogo_Producto_Nombre;
    }

    public Double getCatalogo_Producto_Precio() {
        return catalogo_Producto_Precio;
    }

    public void setCatalogo_Producto_Precio(Double catalogo_Producto_Precio) {
        this.catalogo_Producto_Precio = catalogo_Producto_Precio;
    }

    public String getCatalogo_Producto_Imagen() {
        return catalogo_Producto_Imagen;
    }

    public void setCatalogo_Producto_Imagen(String catalogo_Producto_Imagen) {
        this.catalogo_Producto_Imagen = catalogo_Producto_Imagen;
    }

    public Integer getCatalogo_PuntoVenta_Id() {
        return catalogo_PuntoVenta_Id;
    }

    public void setCatalogo_PuntoVenta_Id(Integer catalogo_PuntoVenta_Id) {
        this.catalogo_PuntoVenta_Id = catalogo_PuntoVenta_Id;
    }

    public String getCatalogo_PuntoVenta_Nombre() {
        return catalogo_PuntoVenta_Nombre;
    }

    public void setCatalogo_PuntoVenta_Nombre(String catalogo_PuntoVenta_Nombre) {
        this.catalogo_PuntoVenta_Nombre = catalogo_PuntoVenta_Nombre;
    }

    public Integer getCatalogo_Stock() {
        return catalogo_Stock;
    }

    public void setCatalogo_Stock(Integer catalogo_Stock) {
        this.catalogo_Stock = catalogo_Stock;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valorDescuento) {
        this.valor = valorDescuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipoDescuento) {
        this.tipo = tipoDescuento;
    }
}
