package com.rovirosa.rovirosa_spring.DTOs.DetalleVenta;

import jakarta.validation.constraints.NotNull;

public class DetalleVentaDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productoId;
    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;
    @NotNull(message = "El precio unitario es obligatorio")
    private Double precioUnitario;
    @NotNull(message = "El descuento unitario es obligatorio")
    private Double descuentoUnitario;

    public Integer getProductoId() {
        return productoId;
    }
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public Double getDescuentoUnitario() {
        return descuentoUnitario;
    }
    public void setDescuentoUnitario(Double descuentoUnitario) {
        this.descuentoUnitario = descuentoUnitario;
    }
    
}
