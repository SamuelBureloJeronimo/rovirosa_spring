package com.rovirosa.rovirosa_spring.DTOs.PuntoVenta;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryDTO;

public class PuntoVentaDetallesDTO {

    private PuntoVentaSimpleQueryDTO puntoVenta;
    private List<RepartidorQueryDTO> repartidores;
    private GerentePvQueryDTO gerente;
    private List<CatalogoQueryDTO> catalogo;

    public PuntoVentaDetallesDTO(PuntoVentaSimpleQueryDTO puntoVenta, List<RepartidorQueryDTO> repartidores, GerentePvQueryDTO gerente, List<CatalogoQueryDTO> catalogo) {
        this.puntoVenta = puntoVenta;
        this.repartidores = repartidores;
        this.gerente = gerente;
        this.catalogo = catalogo;
    }

    public List<CatalogoQueryDTO> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(List<CatalogoQueryDTO> catalogo) {
        this.catalogo = catalogo;
    }

    public PuntoVentaSimpleQueryDTO getPuntoVenta() {
        return puntoVenta;
    }

    public GerentePvQueryDTO getGerente() {
        return gerente;
    }
    public void setGerente(GerentePvQueryDTO gerente) {
        this.gerente = gerente;
    }
    
    public List<RepartidorQueryDTO> getRepartidores() {
        return repartidores;
    }
    public void setRepartidores(List<RepartidorQueryDTO> repartidores) {
        this.repartidores = repartidores;
    }
    public void setPuntoVenta(PuntoVentaSimpleQueryDTO puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
    
}
