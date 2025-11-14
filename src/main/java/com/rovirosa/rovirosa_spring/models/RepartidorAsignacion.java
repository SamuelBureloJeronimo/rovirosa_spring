package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "repart_asign")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RepartidorAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", referencedColumnName = "id")
    private Repartidor rep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", referencedColumnName = "id")
    private PuntoVenta puntoVenta;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaIn;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
}
