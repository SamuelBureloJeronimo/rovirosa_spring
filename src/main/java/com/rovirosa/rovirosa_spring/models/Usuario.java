package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "estado", nullable = false, length = 255)
    private String estado = "activo";

    @Column(name = "created", nullable = false, length = 50)
    private Timestamp created;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol = "CLIENTE";

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(Integer id, Persona persona, String correo, String password, String estado, String rol) {
        this.id = id;
        this.persona = persona;
        this.correo = correo;
        this.password = password;
        this.estado = estado;
        this.rol = rol;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

        public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
