package com.rovirosa.rovirosa_spring.DTOs.Venta;

import java.util.List;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;

public class VentaResponseClienteDTO {

    private VentaQueryClienteDTO venta;
    private List<DetalleVentaResponseDTO> detalleVenta;
    
    public VentaResponseClienteDTO(VentaQueryClienteDTO venta, List<DetalleVentaResponseDTO> detalleVenta) {
        this.venta = venta;
        this.detalleVenta = detalleVenta;
    }

    public VentaQueryClienteDTO getVenta() {
        return venta;
    }
    public void setVenta(VentaQueryClienteDTO venta) {
        this.venta = venta;
    }

    public List<DetalleVentaResponseDTO> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetalleVentaResponseDTO> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

}
