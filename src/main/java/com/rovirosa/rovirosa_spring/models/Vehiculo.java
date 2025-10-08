package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehiculo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "placa", unique = true, nullable = false)
    private String placa;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "capacidad_kg", nullable = false)
    private Double capacidadKg;

    @Column(name = "volumen_m3", nullable = false)
    private Double volumenM3;

    @Column(name = "factor_uso_max", nullable = false)
    private Double factorUsoMax = 0.8;

    // Constructor vacío
    public Vehiculo() { }

    // Constructor con parametros
    public Vehiculo(Integer id, String placa, String marca, String modelo, Boolean activo, String tipo, Double capacidadKg, Double volumenM3) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.activo = activo;
        this.tipo = tipo;
        this.capacidadKg = capacidadKg;
        this.volumenM3 = volumenM3; 
    }

}
