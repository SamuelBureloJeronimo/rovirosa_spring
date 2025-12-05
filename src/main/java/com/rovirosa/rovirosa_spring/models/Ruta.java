package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.time.LocalDateTime;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Entity
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @Table(name = "rutas")
 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = true)
    private PuntoVenta puntoVenta = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", nullable = true)
    private Repartidor repartidor = null;

    @Column(name = "fech_in", nullable = false)
    private String fechaIn = LocalDateTime.now().toString();

    @Column(name = "fech_fin", nullable = true)
    private String fechaFin = null;

    @Column(name = "estado", nullable = false)
    private String estado = "Pendiente"; // ENUM("pendiente", "en_ruta", "finalizada")
    

}
