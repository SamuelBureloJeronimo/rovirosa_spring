package com.rovirosa.rovirosa_spring.DTOs.Ocr;

public class OcrFrontDTO {

    private String curp;
    private String nombre;
    private String app;
    private String apm;
    private String fechaNac;
    private String sexo;

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

    public void setApp(String apellidoP) {
        this.app = apellidoP;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apellidoM) {
        this.apm = apellidoM;
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
    
}
