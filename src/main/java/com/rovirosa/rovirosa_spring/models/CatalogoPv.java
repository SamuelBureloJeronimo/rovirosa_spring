package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogo_pv")
public class CatalogoPv  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "vendidos", nullable = false)
    private Integer vendidos;

    // Constructor vacío
    public CatalogoPv() {
    }
    // Constructor con parámetros
    public CatalogoPv(Integer id, Producto producto, PuntoVenta puntoVenta, Integer stock, Integer vendidos) {
        this.id = id;
        this.producto = producto;
        this.puntoVenta = puntoVenta;
        this.stock = stock;
        this.vendidos = vendidos;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }
    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getVendidos() {
        return vendidos;
    }
    public void setVendidos(Integer vendidos) {
        this.vendidos = vendidos;
    }
    
}
