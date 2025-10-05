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
@Table(name = "descuentos_categ")
public class DescuentoCategoria implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categ_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private DescuentoConfig config;

    // constructor vacío
    public DescuentoCategoria() { }

    // constructor con parámetros
    public DescuentoCategoria(Integer id, Categoria categoria, DescuentoConfig config) {
        this.id = id;
        this.categoria = categoria;
        this.config = config;
    }
    // getters y setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public DescuentoConfig getConfig() {
        return config;
    }

    public void setConfig(DescuentoConfig config) {
        this.config = config;
    }
    
}
