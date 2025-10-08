package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.sql.Time;

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
@Table(name = "horarios_laborales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HorarioLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name = "h_apertura", nullable = false)
    private Time hApertura;

    @Column(name = "h_cierre", nullable = false)
    private Time hCierre;

    @Column(name = "estado", nullable = false)
    private String estado = "abierto"; // abierto, cerrado

    // Constructor vacío
    public HorarioLaboral() { }

    // Constructor con parámetros
    public HorarioLaboral(Integer id, PuntoVenta puntoVenta, String diaSemana, Time hApertura, Time hCierre, String estado) {
        this.id = id;
        this.puntoVenta = puntoVenta;
        this.diaSemana = diaSemana;
        this.hApertura = hApertura;
        this.hCierre = hCierre;
        this.estado = estado;
    }

    // Getters y Setters

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

    public Time gethApertura() {
        return hApertura;
    }

    public void sethApertura(Time hApertura) {
        this.hApertura = hApertura;
    }

    public Time gethCierre() {
        return hCierre;
    }

    public void sethCierre(Time hCierre) {
        this.hCierre = hCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    
    
}
