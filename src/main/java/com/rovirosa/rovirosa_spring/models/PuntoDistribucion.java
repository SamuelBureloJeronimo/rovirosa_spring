package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

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
@Table(name = "puntos_distribucion")
public class PuntoDistribucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direc_id", nullable = false)
    private Direccion direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "config_rfc", nullable = false)
    private EmpresaConfig config;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descrip", length = 255)
    private String descrip;

    // Constructor vacío
    public PuntoDistribucion() {}

    // Constructor con parametros
    public PuntoDistribucion(Integer id, Direccion direccion, EmpresaConfig config, String estado,
            String descrip) {
        this.id = id;
        this.direccion = direccion;
        this.config = config;
        this.estado = estado;
        this.descrip = descrip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    
    
}
