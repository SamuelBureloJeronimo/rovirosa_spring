package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    // Relaciones con Categoria y Marca
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @Column(name = "imagen", nullable = false, length = 100)
    private String imagen;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @Column(name = "vol_m3", nullable = false)
    private BigDecimal volM3;

}
