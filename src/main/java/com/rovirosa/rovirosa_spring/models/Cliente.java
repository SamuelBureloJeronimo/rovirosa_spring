package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

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
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @Column(name = "ine_front", nullable = false, length = 50)
    private String ineFront;

    @Column(nullable = false, length = 50)
    private String ineBack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dir_id", nullable = false)
    private Direccion direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = true)
    private PuntoVenta puntoVenta;

    // Constructor vacío
    public Cliente() {
    }

    public Cliente(Integer id, Usuario usuario, String ineFront, String ineBack, Direccion direccion, PuntoVenta puntoVenta) {
        this.id = id;
        this.usuario = usuario;
        this.ineFront = ineFront;
        this.ineBack = ineBack;
        this.direccion = direccion;
        this.puntoVenta = puntoVenta;
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getId() : "null") +
                ", ineFront='" + ineFront + '\'' +
                ", ineBack='" + ineBack + '\'' +
                ", direccion=" + (direccion != null ? direccion.getId() : "null") +
                '}';
    }

}
