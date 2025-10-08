package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lat", nullable = false, precision = 10, scale = 8)
    private BigDecimal lat;

    @Column(name = "lng", nullable = false, precision = 11, scale = 8)
    private BigDecimal lng;

    @Column(name = "ref", nullable = false, length = 255)
    private String ref;

    // Constructor vacío
    public Direccion() {
    }

    // Constructor con parámetros
    public Direccion(Integer id, BigDecimal lat, BigDecimal lng, String ref) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.ref = ref;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal latitud) {
        this.lat = latitud;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal longitud) {
        this.lng = longitud;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String referencia) {
        this.ref = referencia;
    }
}
