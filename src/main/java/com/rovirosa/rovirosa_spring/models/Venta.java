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
@Table(name = "ventas")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;

    @Column(name = "fecha_inic", nullable = false)
    private Timestamp fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Timestamp fechaFin;

    @Column(name = "calif", nullable = false)
    private Integer calif;

    // Constructor vacío
    public Venta() { }

    // Constructor con parámetros
    public Venta(Integer id, Cliente cliente, Pago pago, Timestamp fechaInicio, Timestamp fechaFin, Integer calif) {
        this.id = id;
        this.cliente = cliente;
        this.pago = pago;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.calif = calif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCalif() {
        return calif;
    }

    public void setCalif(Integer calif) {
        this.calif = calif;
    }

    
}
