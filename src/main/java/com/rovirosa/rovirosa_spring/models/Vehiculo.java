package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private BigDecimal capacidadKg;

    @Column(name = "volumen_m3", nullable = false)
    private BigDecimal volumenM3;

    @Column(name = "factor_uso_max", nullable = false)
    private Double factorUsoMax = 0.8;

}
