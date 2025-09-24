package com.rovirosa.rovirosa_spring.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "curp", length = 18, nullable = false, unique = true)
    private String curp;

    @Column(name = "tel", length = 50, nullable = false, unique = true)
    private String tel;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "app", length = 50, nullable = false)
    private String app;

    @Column(name = "apm", length = 50, nullable = false)
    private String apm;

    @Column(name = "fech_nac", nullable = false)
    private LocalDate fechNac;

    @Column(name = "sexo", nullable = false)
    private Integer sexo; 
    // 1 = Masculino, 2 = Femenino

    // Constructor vacío
    public Persona() {
    }

    // Constructor con parámetros
    public Persona(Integer id, String curp, String tel, String nombre, String app, String apm, LocalDate fechNac, Integer sexo) {
        this.id = id;
        this.curp = curp;
        this.tel = tel;
        this.nombre = nombre;
        this.app = app;
        this.apm = apm;
        this.fechNac = fechNac;
        this.sexo = sexo;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public LocalDate getFechNac() {
        return fechNac;
    }

    public void setFechNac(LocalDate fechNac) {
        this.fechNac = fechNac;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public void printConsole() {
        System.out.println(id+", "+curp+", "+nombre+", "+app+", "+apm+", "+sexo+", "+fechNac+", "+tel);
    }
}
