package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "metodo", nullable = false)
    private String metodo;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "paga_con", nullable = true)
    private Double pagaCon;

    @Column(name = "fecha", nullable = true)
    private Timestamp fecha;

    @Column(name = "compr", nullable = true)
    private String compr;

    @Column(name = "estado", nullable = false)
    private String estado = "pendiente";


    // Constructor vacío
    public Pago() { }

    // Constructor con parámetros
    public Pago(Integer id, String metodo, Double monto, Double pagaCon, Timestamp fecha, String compr, String estado) {
        this.id = id;
        this.metodo = metodo;
        this.monto = monto;
        this.pagaCon = pagaCon;
        this.fecha = fecha;
        this.compr = compr;
        this.estado = estado;  
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getPagaCon() {
        return pagaCon;
    }

    public void setPagaCon(Double pagaCon) {
        this.pagaCon = pagaCon;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getCompr() {
        return compr;
    }

    public void setCompr(String compr) {
        this.compr = compr;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
