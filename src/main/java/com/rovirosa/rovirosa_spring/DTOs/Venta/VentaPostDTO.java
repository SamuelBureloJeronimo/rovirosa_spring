package com.rovirosa.rovirosa_spring.DTOs.Venta;

import java.util.List;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaDTO;
import com.rovirosa.rovirosa_spring.DTOs.Direccion.DireccionPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Pago.PagoDTO;

import jakarta.validation.constraints.NotNull;

public class VentaPostDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;
    
    @NotNull(message = "El ID del punto de venta es obligatorio")
    private Integer pvId;

    @NotNull(message = "Los datos de pago son obligatorios")
    private PagoDTO pago;

    @NotNull(message = "Los detalles de la venta son obligatorios")
    private List<DetalleVentaDTO> detallesVenta;

    @NotNull(message = "La dirección de envío es obligatoria")
    private DireccionPostDTO direccion;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getPvId() {
        return pvId;
    }

    public void setPvId(Integer pvId) {
        this.pvId = pvId;
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }

    public List<DetalleVentaDTO> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVentaDTO> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public DireccionPostDTO getDireccion() {
        return direccion;
    }
    public void setDireccion(DireccionPostDTO direccion) {
        this.direccion = direccion;
    }

}