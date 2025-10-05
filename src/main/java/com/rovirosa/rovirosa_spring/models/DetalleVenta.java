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
@Table(name = "detalles_venta")
public class DetalleVenta implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vnta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id", nullable = false)
    private Producto producto;

    @Column(name = "cant_in", nullable = false)
    private Integer cantIn;

    @Column(name = "cant_fin", nullable = false)
    private Integer cantFin;

    @Column(name = "precio_unit", nullable = false)
    private Double precioUnit;

    @Column(name = "desc", nullable = false)
    private Double desc;

    // Constructor vacío
    public DetalleVenta() { }

    // Constructor con parámetros
    public DetalleVenta(Integer id, Venta venta, Producto producto, Integer cantIn, Integer cantFin, Double precioUnit, Double desc) {
        this.id = id;
        this.venta = venta;
        this.producto = producto;
        this.cantIn = cantIn;
        this.cantFin = cantFin;
        this.precioUnit = precioUnit;
        this.desc = desc;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantIn() {
        return cantIn;
    }

    public void setCantIn(Integer cantIn) {
        this.cantIn = cantIn;
    }

    public Integer getCantFin() {
        return cantFin;
    }

    public void setCantFin(Integer cantFin) {
        this.cantFin = cantFin;
    }

    public Double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(Double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public Double getDesc() {
        return desc;
    }

    public void setDesc(Double desc) {
        this.desc = desc;
    }

}
