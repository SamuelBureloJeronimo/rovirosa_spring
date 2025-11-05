package com.rovirosa.rovirosa_spring.DTOs.Descuento;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DescuentoPostDTO {

    @NotNull(message = "El id no puede ser nulo")
    private Integer id;    
    @NotBlank(message = "El tipo no puede estar vacío")    
    private String tipo;
    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;
    @NotBlank(message = "El objetivo no puede estar vacío")
    private String objetivo;
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDate fechaIn;
    private LocalDate fechaFin;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }  
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    public LocalDate getFechaIn() {
        return fechaIn;
    }
    public void setFechaIn(LocalDate fechaIn) {
        this.fechaIn = fechaIn;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

}
