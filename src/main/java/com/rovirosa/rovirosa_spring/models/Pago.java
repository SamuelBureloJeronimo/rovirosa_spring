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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    
}
