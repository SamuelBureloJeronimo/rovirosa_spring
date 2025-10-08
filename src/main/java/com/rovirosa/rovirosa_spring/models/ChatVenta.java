package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.security.Timestamp;

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
@Table(name = "chat_venta")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vnta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @Column(name = "enviado", nullable = false)
    private Timestamp enviado;

    @Column(name = "archivo", nullable = true, length = 50)
    private String archivo;

    // Constructor vacío
    public ChatVenta() { }

    // Constructor con parámetros
    public ChatVenta(Integer id, Venta venta, Usuario user, String mensaje, Timestamp enviado, String archivo) {
        this.id = id;
        this.venta = venta;
        this.user = user;
        this.mensaje = mensaje;
        this.enviado = enviado;
        this.archivo = archivo;
    }

    // Getters y Setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getEnviado() {
        return enviado;
    }

    public void setEnviado(Timestamp enviado) {
        this.enviado = enviado;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
}
