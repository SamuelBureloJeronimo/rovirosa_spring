package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "empresa_config")
public class EmpresaConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rfc", length = 20, nullable = false)
    private String rfc;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "logo", length = 255, nullable = false)
    private String logo;

    @Column(name = "descrip", length = 255, nullable = false)
    private String descrip;

    @Column(name = "monto_min", nullable = false)
    private Integer montoMin;

    @Column(name = "email_app", length = 150)
    private String emailApp;

    @Column(name = "codigo_app", length = 50)
    private String codigoApp;

    // Constructor vacío
    public EmpresaConfig() {
    }

    // Constructor con parámetros
    public EmpresaConfig(String rfc, String nombre, String logo, String descrip,
                         Integer montoMin, String emailApp, String codigoApp) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.logo = logo;
        this.descrip = descrip;
        this.montoMin = montoMin;
        this.emailApp = emailApp;
        this.codigoApp = codigoApp;
    }

    // Getters y Setters
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Integer getMontoMin() {
        return montoMin;
    }

    public void setMontoMin(Integer montoMin) {
        this.montoMin = montoMin;
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
