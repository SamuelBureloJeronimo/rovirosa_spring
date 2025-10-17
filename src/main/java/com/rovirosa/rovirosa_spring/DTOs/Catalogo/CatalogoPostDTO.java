package com.rovirosa.rovirosa_spring.DTOs.Catalogo;

import jakarta.validation.constraints.NotNull;

public class CatalogoPostDTO {
    
    @NotNull(message = "El ID del producto no puede ser nulo")
    private Integer producto_id;

    @NotNull(message = "El ID del punto de venta no puede ser nulo")
    private Integer puntoVenta_id;

    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock;

    public Integer getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
    }

    public Integer getPuntoVenta_id() {
        return puntoVenta_id;
    }

    public void setPuntoVenta_id(Integer puntoVenta_id) {
        this.puntoVenta_id = puntoVenta_id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    

}
