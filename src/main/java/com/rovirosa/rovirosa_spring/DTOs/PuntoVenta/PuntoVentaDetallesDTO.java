package com.rovirosa.rovirosa_spring.DTOs.PuntoVenta;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DiaLaboral.DiaLaboralResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorQueryViewDTO;

public class PuntoVentaDetallesDTO {

    private PuntoVentaSimpleQueryDTO puntoVenta;
    private List<RepartidorQueryViewDTO> repartidores;
    private GerentePvQueryDTO gerente;
    private List<CatalogoQueryDTO> catalogo;
    private List<DiaLaboralResponseDTO> dias;

    public PuntoVentaDetallesDTO(PuntoVentaSimpleQueryDTO puntoVenta, List<RepartidorQueryViewDTO> repartidores, GerentePvQueryDTO gerente, List<CatalogoQueryDTO> catalogo, List<DiaLaboralResponseDTO> dias) {
        this.puntoVenta = puntoVenta;
        this.repartidores = repartidores;
        this.gerente = gerente;
        this.catalogo = catalogo;
        this.dias = dias;
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
    
    public List<RepartidorQueryViewDTO> getRepartidores() {
        return repartidores;
    }
    public void setRepartidores(List<RepartidorQueryViewDTO> repartidores) {
        this.repartidores = repartidores;
    }
    public void setPuntoVenta(PuntoVentaSimpleQueryDTO puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public List<DiaLaboralResponseDTO> getDias() {
        return dias;
    }
    public void setDias(List<DiaLaboralResponseDTO> dias) {
        this.dias = dias;
    }

}
