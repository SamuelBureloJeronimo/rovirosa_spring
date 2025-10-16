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
@Table(name = "puntos_venta")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PuntoVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direc_id", nullable = false)
    private Direccion direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_rfc", nullable = false)
    private EmpresaConfig config;

    @Column(name = "zona_permitida", nullable = true)
    private String zonaPermitida;

    @Column(name = "estado")
    private String estado = "habilitado";

    // Constructor vacío
    public PuntoVenta() {}

    // Constructor con parametros
    public PuntoVenta(Integer id, String nombre, Direccion direccion, EmpresaConfig config, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.config = config;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public EmpresaConfig getConfig() {
        return config;
    }

    public void setConfig(EmpresaConfig config) {
        this.config = config;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getZonaPermitida() {
        return zonaPermitida;
    }

    public void setZonaPermitida(String zonaPermitida) {
        this.zonaPermitida = zonaPermitida;
    }

    
    
}
