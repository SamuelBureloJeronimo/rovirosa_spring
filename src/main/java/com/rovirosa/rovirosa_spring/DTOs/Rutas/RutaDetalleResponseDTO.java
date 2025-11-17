package com.rovirosa.rovirosa_spring.DTOs.Rutas;

import java.math.BigDecimal;
import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RutaDetalleResponseDTO {

    private Integer id;
    private Integer venta_Id;
    private String venta_FechaInicio;
    private String venta_Pago_Metodo;
    private Double venta_Pago_PagaCon;
    private Double venta_Pago_Monto;
    private BigDecimal lat;
    private BigDecimal lng;
    private String ref;

    private List<DetalleVentaResponseDTO> productos;

    public RutaDetalleResponseDTO(RutaDetalleQueryByRepartidorIdDTO dto) {
        this.id = dto.getId();
        this.venta_Id = dto.getVenta_Id();
        this.venta_FechaInicio = dto.getVenta_FechaInicio();
        this.venta_Pago_Metodo = dto.getVenta_Pago_Metodo();
        this.venta_Pago_PagaCon = dto.getVenta_Pago_PagaCon();
        this.venta_Pago_Monto = dto.getVenta_Pago_Monto();
        this.lat = dto.getLat();
        this.lng = dto.getLng();
        this.ref = dto.getRef();

    }

}
