package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuentos_producto")
public class DescuentoProducto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private DescuentoConfig config;

    // Constructor vacío
    public DescuentoProducto() { }

    // Constructor con parámetros
    public DescuentoProducto(Integer id, Producto producto, DescuentoConfig config) {
        this.id = id;
        this.producto = producto;
        this.config = config;
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

    public DescuentoConfig getConfig() {
        return config;
    }

    public void setConfig(DescuentoConfig config) {
        this.config = config;
    }

    
}
