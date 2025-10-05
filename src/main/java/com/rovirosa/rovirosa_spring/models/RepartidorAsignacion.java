package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "repart_asign")
public class RepartidorAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", referencedColumnName = "id")
    private Repartidor rep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", referencedColumnName = "id")
    private PuntoVenta pv;

    @Column(name = "fecha_in", nullable = false)
    private LocalDate fechaIn;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    // Constructor vacío
    public RepartidorAsignacion() {}

    // Constructor con parámetros
    public RepartidorAsignacion(Integer id, Repartidor rep, PuntoVenta pv, LocalDate fechaIn, LocalDate fechaFin, Boolean activo) {
        this.id = id;
        this.rep = rep;
        this.pv = pv;
        this.fechaIn = fechaIn;
        this.fechaFin = fechaFin;
        this.activo = activo;
    }

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

    public PuntoVenta getPv() {
        return pv;
    }

    public void setPv(PuntoVenta pv) {
        this.pv = pv;
    }

    public LocalDate getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(LocalDate fechaIn) {
        this.fechaIn = fechaIn;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }   
    
}
