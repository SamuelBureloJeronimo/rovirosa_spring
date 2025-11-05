package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

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

@Entity
@Table(name = "horarios_laborales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HorarioLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_id", nullable = false)
    private DiaLaboral diaLaboral;

    @Column(name = "h_apertura", nullable = true)
    private String hApertura;

    @Column(name = "h_cierre", nullable = true)
    private String hCierre;

    // Constructor vacío
    public HorarioLaboral() { }

    // Constructor con parámetros
    public HorarioLaboral(Integer id, DiaLaboral diaLaboral, String hApertura, String hCierre) {
        this.id = id;
        this.diaLaboral = diaLaboral;
        this.hApertura = hApertura;
        this.hCierre = hCierre;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DiaLaboral getDiaLaboral() {
        return diaLaboral;
    }
    public void setDiaLaboral(DiaLaboral diaLaboral) {
        this.diaLaboral = diaLaboral;
    }

    public String gethApertura() {
        return hApertura;
    }

    public void sethApertura(String hApertura) {
        this.hApertura = hApertura;
    }

    public String gethCierre() {
        return hCierre;
    }

    public void sethCierre(String hCierre) {
        this.hCierre = hCierre;
    }   
    
}
