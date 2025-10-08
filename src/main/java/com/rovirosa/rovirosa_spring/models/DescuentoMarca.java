package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuentos_marca")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DescuentoMarca implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private DescuentoConfig config;

    // Constructor vacio
    public DescuentoMarca() { }

    // Constructor con parametros
    public DescuentoMarca(Integer id, Marca marca, DescuentoConfig config) {
        this.id = id;
        this.marca = marca;
        this.config = config;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public DescuentoConfig getConfig() {
        return config;
    }

    public void setConfig(DescuentoConfig config) {
        this.config = config;
    }

    
    
}
