package com.rovirosa.rovirosa_spring.DTOs.Usuarios;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class UsuarioByRolPostDTO {
    @NotBlank(message = "CURP es obligatorio")
    private String curp;
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "Apellido paterno es obligatorio")
    private String app;
    @NotBlank(message = "Apellido materno es obligatorio")
    private String apm;
    @NotBlank(message = "Teléfono es obligatorio")
    private String tel;
    @NotBlank(message = "Fecha de nacimiento es obligatorio")
    private LocalDate fechaNac;
    @NotBlank(message = "Sexo es obligatorio")
    private String sexo;

    @NotBlank(message = "Correo es obligatorio")
    private String correo;
    @NotBlank(message = "Contraseña es obligatorio")
    private String password;

    @NotBlank(message = "Rol es obligatorio")
    private String rol;

    private Integer pv_id;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getPv_id() {
        return pv_id;
    }

    public void setPv_id(Integer pv_id) {
        this.pv_id = pv_id;
    }
}
