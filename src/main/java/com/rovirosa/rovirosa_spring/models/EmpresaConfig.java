package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "empresa_config")
public class EmpresaConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "max_prod", nullable = false)
    private Integer maxProd;

    @Column(name = "email_app", length = 150)
    private String emailApp;

    @Column(name = "codigo_app", length = 50)
    private String codigoApp;

    // Constructor vacío
    public EmpresaConfig() {
    }

    // Constructor con parámetros
    public EmpresaConfig(Integer id, Integer maxProd, String emailApp, String codigoApp) {
        this.id = id;
        this.maxProd = maxProd;
        this.emailApp = emailApp;
        this.codigoApp = codigoApp;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxProd() {
        return maxProd;
    }

    public void setMaxProd(Integer maxProd) {
        this.maxProd = maxProd;
    }

    public String getEmailApp() {
        return emailApp;
    }

    public void setEmailApp(String emailApp) {
        this.emailApp = emailApp;
    }

    public String getCodigoApp() {
        return codigoApp;
    }

    public void setCodigoApp(String codigoApp) {
        this.codigoApp = codigoApp;
    }
}
