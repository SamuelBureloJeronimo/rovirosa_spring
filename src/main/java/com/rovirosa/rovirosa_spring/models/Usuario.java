package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_id", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id", nullable = true)
    private PuntoVenta puntoVenta;

    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "token_fbm", nullable = true, length = 255)
    private String tokenFbm;

    @Column(name = "estado", nullable = false, length = 255)
    private String estado = "activo";

    @Column(name = "created", nullable = false, length = 50)
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    @Column(name = "rol", nullable = false, length = 20)
    private String rol = "CLIENTE";

}
