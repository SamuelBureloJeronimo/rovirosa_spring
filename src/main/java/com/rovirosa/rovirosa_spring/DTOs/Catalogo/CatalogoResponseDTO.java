package com.rovirosa.rovirosa_spring.DTOs.Catalogo;

import com.rovirosa.rovirosa_spring.models.Marca;

public class CatalogoResponseDTO {

    private Integer id;
    private Integer producto_Id;
    private String nombre;
    private Double precio;
    private String imagen;
    private String tipo;
    private Marca marca;
    private Double valor;
    private Integer stock;
    private Integer vendidos;

    // Constructor
    public CatalogoResponseDTO(CatalogoQueryDTO catalogo, Double valor, String tipo) {
        this.id = catalogo.getId();
        this.producto_Id = catalogo.getProducto_Id();
        this.nombre = catalogo.getProducto_Nombre();
        this.precio = catalogo.getProducto_Precio();
        this.imagen = catalogo.getProducto_Imagen();
        this.marca = catalogo.getProducto_Marca();
        this.valor = valor;
        this.tipo = tipo;
        this.stock = catalogo.getStock();
        this.vendidos = catalogo.getVendidos();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProducto_Id() {
        return producto_Id;
    }

    public void setProducto_Id(Integer producto_Id) {
        this.producto_Id = producto_Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Integer getVendidos() {
        return vendidos;
    }

    public void setVendidos(Integer vendidos) {
        this.vendidos = vendidos;
    }
}
