package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

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
@Table(name = "dias_laborales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DiaLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    // Constructor vacío
    public DiaLaboral() { }

    // Constructor con parámetros
    public DiaLaboral(Integer id, PuntoVenta puntoVenta, String diaSemana) {
        this.id = id;
        this.puntoVenta = puntoVenta;
        this.diaSemana = diaSemana;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    

}
