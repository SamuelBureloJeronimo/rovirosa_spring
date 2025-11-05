package com.rovirosa.rovirosa_spring.DTOs.Auth.CreateAccount;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountDTO {

    // Datos de Persona
    @NotBlank
    private String curp;
    @NotBlank
    private String nombre;
    @NotBlank
    private String tel;
    @NotBlank
    private String app;
    @NotBlank
    private String apm;
    @NotBlank
    private String fechaNac;
    @NotBlank
    private String sexo;

    // Datos de Usuario
    @NotBlank
    private String correo;
    @NotBlank
    private String password;
    @NotNull
    private Integer puntoVentaId;

    // Datos de la dirección
    @NotNull
    private BigDecimal lat;
    @NotNull
    private BigDecimal lng;
    @NotBlank
    private String ref;

    // Getters y Setters
    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public Integer getPuntoVentaId() {
        return puntoVentaId;
    }

    public void setPuntoVentaId(Integer puntoVentaId) {
        this.puntoVentaId = puntoVentaId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
