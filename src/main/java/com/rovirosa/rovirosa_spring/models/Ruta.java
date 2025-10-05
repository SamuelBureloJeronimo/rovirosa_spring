package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.security.Timestamp;

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
 @Table(name = "rutas")
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", nullable = false)
    private Repartidor rep;

    @Column(name = "fecha_in", nullable = false)
    private Timestamp fechaIn;

    @Column(name = "fecha_fin", nullable = true)
    private Timestamp fechaFin;

    @Column(name = "estado", nullable = false)
    private String estado; // ENUM("pendiente", "en_ruta", "finalizada")

    // Constructor vacío
    public Ruta() {}

    // Constructor con parámetros
    public Ruta(Integer id, Repartidor rep, Timestamp fechaIn, Timestamp fechaFin, String estado) {
        this.id = id;
        this.rep = rep;
        this.fechaIn = fechaIn;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Repartidor getRep() {
        return rep;
    }

    public void setRep(Repartidor rep) {
        this.rep = rep;
    }

    public Timestamp getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Timestamp fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}
