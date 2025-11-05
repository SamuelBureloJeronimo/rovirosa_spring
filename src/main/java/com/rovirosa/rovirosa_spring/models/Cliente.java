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

@Entity
@Table(name = "clientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dir_id", nullable = false)
    private Direccion direccion;

    @Column(name = "ine_front", nullable = false, length = 50)
    private String ineFront;

    @Column(name = "ine_back", nullable = false, length = 50)
    private String ineBack;

    @Column(name = "stricks", nullable = false)
    private Integer stricks = 0;

    @Column(name = "p_cancel", nullable = false)
    private Integer pCancel = 0;

    @Column(name = "last_cancel", nullable = true)
    private LocalDate lastCancel;

    // Constructor vacío
    public Cliente() { }

    // Constructor con parámetros
    public Cliente(Integer id, Usuario usuario, Direccion direccion, String ineFront, String ineBack, Integer stricks, Integer pCancel, LocalDate lastCancel) {
        this.id = id;
        this.usuario = usuario;
        this.direccion = direccion;
        this.ineFront = ineFront;
        this.ineBack = ineBack;
        this.stricks = stricks;
        this.pCancel = pCancel;
        this.lastCancel = lastCancel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getIneFront() {
        return ineFront;
    }

    public void setIneFront(String ineFront) {
        this.ineFront = ineFront;
    }

    public String getIneBack() {
        return ineBack;
    }

    public void setIneBack(String ineBack) {
        this.ineBack = ineBack;
    }

    public Integer getStricks() {
        return stricks;
    }

    public void setStricks(Integer stricks) {
        this.stricks = stricks;
    }

    public Integer getpCancel() {
        return pCancel;
    }

    public void setpCancel(Integer pCancel) {
        this.pCancel = pCancel;
    }

    public LocalDate getLastCancel() {
        return lastCancel;
    }

    public void setLastCancel(LocalDate lastCancel) {
        this.lastCancel = lastCancel;
    }

}
