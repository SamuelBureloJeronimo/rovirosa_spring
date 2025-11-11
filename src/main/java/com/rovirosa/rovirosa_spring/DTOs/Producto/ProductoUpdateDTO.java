package com.rovirosa.rovirosa_spring.DTOs.Producto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class ProductoUpdateDTO {

    @NotNull(message = "Debe indicar el ID del producto a actualizar")
    private Integer id;

    private String nombre;
    private Integer marcaId;
    private Double precio;
    private Double pesoKg;
    private BigDecimal volM3;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getMarcaId() { return marcaId; }
    public void setMarcaId(Integer marcaId) { this.marcaId = marcaId; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getVolM3() { return volM3; }
    public void setVolM3(BigDecimal volM3) { this.volM3 = volM3; }
}

