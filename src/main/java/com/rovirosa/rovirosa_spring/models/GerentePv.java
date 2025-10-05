package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gerentes_pv")
public class GerentePv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Usuario user;

    @Column(name = "pv_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
    
    // Constructor vacío
    public GerentePv() { }

    // Constructor con parámetros
    public GerentePv(Integer id, Usuario user, PuntoVenta puntoVenta, Boolean activo) {
        this.id = id;
        this.user = user;
        this.puntoVenta = puntoVenta;
        this.activo = activo;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}
