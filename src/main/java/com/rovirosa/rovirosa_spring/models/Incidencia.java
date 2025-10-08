package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "incidencias")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Incidencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id", referencedColumnName = "id")
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", referencedColumnName = "id")
    private Repartidor rep;

    @Column(name = "tipo", nullable = false)   
    private String tipo;

    @Column(name = "descrip", length = 255)
    private String descrip;

    @Column(name = "lat")
    private BigDecimal lat;
    
    @Column(name = "lng")
    private BigDecimal lng;

    @Column(name = "estado", nullable = false)
    private String estado;

    // Constructor vacío
    public Incidencia() {
    }

    // Constructor con paramétros
    public Incidencia(Integer id, Ruta ruta, Repartidor rep, String tipo, String descrip, BigDecimal lat, BigDecimal lng, String estado) {
        this.id = id;
        this.ruta = ruta;
        this.rep = rep;
        this.tipo = tipo;
        this.descrip = descrip;
        this.lat = lat;
        this.lng = lng;
        this.estado = estado;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Repartidor getRep() {
        return rep;
    }

    public void setRep(Repartidor rep) {
        this.rep = rep;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
