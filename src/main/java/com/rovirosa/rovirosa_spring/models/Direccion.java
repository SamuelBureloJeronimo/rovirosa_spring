package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;

    @Column(nullable = false, length = 255)
    private String referencia;

    // Constructor vacío
    public Direccion() {
    }

    // Constructor con parámetros
    public Direccion(Integer id, BigDecimal latitud, BigDecimal longitud, String referencia) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.referencia = referencia;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
