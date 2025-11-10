package com.rovirosa.rovirosa_spring.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaQueryClienteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaResponseClienteDTO;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.DetalleVenta;
import com.rovirosa.rovirosa_spring.models.Pago;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Venta;
import com.rovirosa.rovirosa_spring.repositories.DetalleVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.PagoRepository;
import com.rovirosa.rovirosa_spring.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRep;
    @Autowired
    private DetalleVentaRepository detalleVentaRep;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PagoRepository pagoRep;

    @Transactional
    public void crearVenta(VentaPostDTO ventaDTO, MultipartFile comprobante) {
        
        Cliente cliente = new Cliente();
        cliente.setId(ventaDTO.getClienteId());
        
        PuntoVenta puntoVenta = new PuntoVenta();
        puntoVenta.setId(ventaDTO.getPvId());

        Pago pago = new Pago();
        pago.setMonto(ventaDTO.getPago().getMonto());
        pago.setMetodo(ventaDTO.getPago().getMetodo());

        String nameCompr = storageService.generateFileName();

        if(pago.getMetodo().equalsIgnoreCase("transferencia") && comprobante != null) {
            pago.setCompr("comprobantes/"+nameCompr);
        } else {
            pago.setCompr(null);
        }

        pago = pagoRep.save(pago);
        
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setPuntoVenta(puntoVenta);
        venta.setPago(pago);
        venta.setFechaInicio(LocalDate.now().toString());

        venta = ventaRep.save(venta);

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetallesVenta()) {
            Producto producto = new Producto();
            producto.setId(detalleDTO.getProductoId());

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantIn(detalleDTO.getCantidad());
            detalle.setPrecioUnit(detalleDTO.getPrecioUnitario());
            detalle.setDescUnit(detalleDTO.getDescuentoUnitario());
            detalleVentaRep.save(detalle);
        }

        storageService.store(comprobante, "comprobantes/", nameCompr);
    }

    public List<VentaResponseClienteDTO> getVentasByClienteId(Integer clienteId) {
        List<VentaQueryClienteDTO> ventas = ventaRep.findByCliente_Id(clienteId);
        return ventas.stream().map(venta -> new VentaResponseClienteDTO(venta, detalleVentaRep.findByVenta_Id(venta.getId()))).toList();
    }
    
}
