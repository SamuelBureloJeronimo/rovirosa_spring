package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "descuentos_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DescuentoConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "objetivo", nullable = false)
    private String objetivo;

    @Column(name = "fech_in", nullable = false)
    private LocalDate fechaIn;

    @Column(name = "fech_fin", nullable = true)
    private LocalDate fechaFin;

    @Column(name = "banner", nullable = true)
    private String banner;

}
