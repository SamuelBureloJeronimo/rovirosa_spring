package com.rovirosa.rovirosa_spring.DTOs.PuntoVenta;

import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;

public class PuntoVentaResponseDTO {

    private Integer id;
    private String nombre;
    private String estado;
    private Direccion direccion;
    private String zonaPermitida;

    // Constructor
    public PuntoVentaResponseDTO(PuntoVentaQueryDTO punto) {
        this.id = punto.getId();
        this.nombre = punto.getNombre();
        this.estado = punto.getEstado();
        this.direccion = punto.getDireccion();
        this.zonaPermitida = punto.getZonaPermitida();
    }

    public PuntoVentaResponseDTO(PuntoVenta punto) {
        this.id = punto.getId();
        this.nombre = punto.getNombre();
        this.estado = punto.getEstado();
        this.direccion = punto.getDireccion();
        this.zonaPermitida = punto.getZonaPermitida();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getZonaPermitida() {
        return zonaPermitida;
    }

    public void setZonaPermitida(String zonaPermitida) {
        this.zonaPermitida = zonaPermitida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
