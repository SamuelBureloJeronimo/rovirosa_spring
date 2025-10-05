package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "estados_envio")
public class EstadoEnvio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vnta_id", nullable = false)
    private Venta venta;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "fecha", nullable = false)
    private Timestamp fecha;

    // Constructor vacío
    public EstadoEnvio() { }

    // Constructor con parámetros
    public EstadoEnvio(Integer id, Venta venta, Integer estado, Timestamp fecha) {
        this.id = id;
        this.venta = venta;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
