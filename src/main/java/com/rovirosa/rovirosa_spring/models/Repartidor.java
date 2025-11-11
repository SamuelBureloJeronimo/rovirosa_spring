package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "repartidores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Repartidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veh_id", nullable = true)
    private Vehiculo vehiculo;

    @Column(name = "lat", nullable = true)
    private BigDecimal lat;

    @Column(name = "lng", nullable = true)
    private BigDecimal lng;

    @Column(name = "estado", nullable = true)
    private String estado; // ENUM("en_espera", "cargando", "en_ruta", "descansando")

}
