package com.rovirosa.rovirosa_spring.DTOs.Pago;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PagoDTO {
    
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodo;
    
    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    private Double pagaCon;

    public String getMetodo() {
        return metodo;
    }
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }
    public Double getPagaCon() {
        return pagaCon;
    }
    public void setPagaCon(Double pagaCon) {
        this.pagaCon = pagaCon;
    }
}
