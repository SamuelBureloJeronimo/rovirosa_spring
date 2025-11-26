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
    private Integer rutaId;
    private Integer venta_Id;
    private String rutaEstado;
    private String venta_FechaInicio;
    private Integer venta_Pago_Id;
    private String venta_Pago_Metodo;
    private Double venta_Pago_PagaCon;
    private Double venta_Pago_Monto;
    private String venta_Pago_Estado;
    private String venta_Comp;
    private String venta_Estado;
    private BigDecimal lat;
    private BigDecimal lng;
    private String ref;

    private List<DetalleVentaResponseDTO> productos;

    public RutaDetalleResponseDTO(RutaDetalleQueryByRepartidorIdDTO dto) {
        this.id = dto.getId();
        this.rutaId = dto.getRuta_Id();
        this.rutaEstado = dto.getRuta_Estado();
        this.venta_Id = dto.getVenta_Id();
        this.venta_Estado = dto.getVenta_Estado();
        this.venta_Pago_Estado = dto.getVenta_Pago_Estado();
        this.venta_Pago_Id = dto.getVenta_Pago_Id();
        this.venta_FechaInicio = dto.getVenta_FechaInicio();
        this.venta_Pago_Metodo = dto.getVenta_Pago_Metodo();
        this.venta_Pago_PagaCon = dto.getVenta_Pago_PagaCon();
        this.venta_Pago_Monto = dto.getVenta_Pago_Monto();
        this.venta_Comp = dto.getVenta_Pago_Compr();
        this.lat = dto.getLat();
        this.lng = dto.getLng();
        this.ref = dto.getRef();

    }

}
