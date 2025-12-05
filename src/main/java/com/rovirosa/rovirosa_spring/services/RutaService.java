package com.rovirosa.rovirosa_spring.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaStartPutDTO;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Ruta;
import com.rovirosa.rovirosa_spring.models.RutaDetalle;
import com.rovirosa.rovirosa_spring.models.Venta;
import com.rovirosa.rovirosa_spring.repositories.DetalleVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaDetalleRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaRepository;
import com.rovirosa.rovirosa_spring.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRep;
    @Autowired
    private RutaDetalleRepository rutaDetRep;
    @Autowired
    private RepartidorRepository repRep;
    @Autowired
    private VentaRepository ventaRep;
    @Autowired
    private DetalleVentaRepository detVenRep;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private SimpMessagingTemplate template;

    public List<RutaQueryDTO> getAllRutasByPvId(Integer pvId) {
        return rutaRep.findRutasActivas(List.of("pendiente", "en_ruta"), pvId);
    }

    @Transactional
    public ApiResponse<String> finalizarRutaRepartidor(RutaStartPutDTO dto) {
        ApiResponse<String> response = new ApiResponse<>();
        // Actualizar el estado del repartdior a "disponible".
        Integer result = repRep.updateEstado(dto.getRepId(), "en_espera");
        if (result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado del repartidor");
            return response;
        }
        System.out.println("Repartidor actualizado a disponible");
        // Actualizar el estado de la ruta a "finalizada".
        result = rutaRep.updateFinishRuta(dto.getRutaId(), (LocalDateTime.now()).toString(), "finalizada");
        if (result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado de la ruta");
            return response;
        }
        System.out.println("Ruta actualizada a finalizada");
        // Actualizar el estado de cada venta a "Entregado".
        for (Integer ventaId : dto.getVentasId()) {
            // Llamar al servicio de venta para actualizar el estado.
            result = ventaRep.updateEstadoVenta(ventaId, "Entregado");
            if (result == 0) {
                response.setSuccess(false);
                response.setMessage("No se pudo actualizar el estado de la venta ID: " + ventaId);
                return response;
            }
            System.out.println("Venta ID " + ventaId + " actualizada a Entregado");
        }

        System.out.println("Todas las ventas actualizadas a Entregado");
        response.setSuccess(true);
        response.setMessage("Ruta finalizada correctamente");

        this.searchPedidosPendientes(dto);
        // Buscar los pedidos con estado pendiente y emep
        return response;
    }

    @Transactional
    public ApiResponse<String> IniciarRutaRepartidor(RutaStartPutDTO dto) {
        ApiResponse<String> response = new ApiResponse<>();
        // Actualizar el estado del repartdior a "en_ruta".
        Integer result = repRep.updateEstado(dto.getRepId(), "en_ruta");
        if (result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado del repartidor");
            return response;
        }
        System.out.println("Repartidor actualizado a en_ruta");
        // Actualizar el estado de la ruta a "en_ruta".
        result = rutaRep.updateEstadoRuta(dto.getRutaId(), "en_ruta");
        if (result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estado de la ruta");
            return response;
        }
        System.out.println("Ruta actualizada a en_ruta");
        // Actualizar el estado de cada venta a "En_camino".
        for (Integer ventaId : dto.getVentasId()) {
            // Llamar al servicio de venta para actualizar el estado.
            result = ventaRep.updateEstadoVenta(ventaId, "En_camino");
            if (result == 0) {
                response.setSuccess(false);
                response.setMessage("No se pudo actualizar el estado de la venta ID: " + ventaId);
                return response;
            }
            System.out.println("Venta ID " + ventaId + " actualizada a En_camino");
            // Notificar al cliente que su pedido está en camino.
            Venta tokenFmc = ventaRep.findById(ventaId).orElse(null);
            if (tokenFmc != null)
                notificacionService.create(tokenFmc.getId(), "Pedido en camino",
                        "Tu pedido está en camino y llegará pronto.");
        }

        System.out.println("Todas las ventas actualizadas y notificaciones enviadas");
        response.setSuccess(true);
        response.setMessage("Ruta iniciada correctamente");
        return response;
    }

    private void searchPedidosPendientes(RutaStartPutDTO dto) {
        System.out.println("\n\nIniciando proceso de búsqueda de pedidos pendientes...");
        // Buscar pedidos pendientes en el punto de venta del repartidor que finalizó la
        // ruta
        List<RutaDetalle> pendientes = rutaDetRep.findByRutaIsNullAndVenta_PuntoVenta_Id(dto.getPvId());
        // Si no hay pendientes, salir
        if (pendientes.isEmpty()) {
            System.out.println("No hay pedidos pendientes en el punto de venta ID: " + dto.getPvId());
            return;
        }

        // Buscar el repartidor que finalizó la ruta
        Repartidor repartidor = repRep.findById(dto.getRepId()).orElse(null);
        if (repartidor == null) {
            System.out.println("No se encontró el repartidor, no se pueden asignar pedidos pendientes.");
            return;
        }

        // Crear nueva ruta de entrega
        Ruta ruta = new Ruta();
        PuntoVenta pv = new PuntoVenta();
        pv.setId(dto.getPvId());
        ruta.setPuntoVenta(pv);
        ruta.setRepartidor(repartidor);
        ruta = rutaRep.save(ruta);

        // Asignar repartidor a la ruta
        repartidor.setEstado("cargando");
        repRep.save(repartidor);

        // Consultar capacidad del vehiculo del repartidor
        BigDecimal capacidadKg_Vehiculo = repartidor.getVehiculo().getCapacidadKg();
        BigDecimal volumenM3_Vehiculo = repartidor.getVehiculo().getVolumenM3();

        // Agregar factor de uso maximo
        capacidadKg_Vehiculo = capacidadKg_Vehiculo
                .multiply(BigDecimal.valueOf(repartidor.getVehiculo().getFactorUsoMax()));
        volumenM3_Vehiculo = volumenM3_Vehiculo
                .multiply(BigDecimal.valueOf(repartidor.getVehiculo().getFactorUsoMax()));

        List<Integer> ventasAsignadas = new ArrayList<>();

        // Recorrer los pedidos pendientes y tratar de asignarlos al repartidor
        for (RutaDetalle rd : pendientes) {

            // Buscar el detalle de la venta ya fue asignada
            if (ventasAsignadas.contains(rd.getVenta().getId())) {
                rd.setRuta(ruta);
                rutaDetRep.save(rd);
                System.out.println("Pedido ID: " + rd.getVenta().getId() + " ya ha sido asignado.");
                continue;
            }

            // Obtener todos los productos de la venta
            List<DetalleVentaResponseDTO> detVen = detVenRep.findByVenta_Id(rd.getVenta().getId());

            BigDecimal pesoTotal = BigDecimal.ZERO;
            BigDecimal volumenTotal = BigDecimal.ZERO;

            // Calcular peso y volumen total del detalle de venta
            for (DetalleVentaResponseDTO dv : detVen) {
                pesoTotal = pesoTotal.add(dv.getProducto_PesoKg() != null
                        ? BigDecimal.valueOf(dv.getProducto_PesoKg()).multiply(BigDecimal.valueOf(dv.getCantIn()))
                        : BigDecimal.ZERO);
                volumenTotal = volumenTotal.add(dv.getProducto_VolM3() != null
                        ? BigDecimal.valueOf(dv.getProducto_VolM3()).multiply(BigDecimal.valueOf(dv.getCantIn()))
                        : BigDecimal.ZERO);
            }

            // Validar que tenga espacio
            if (pesoTotal.compareTo(capacidadKg_Vehiculo) < 0 &&
                    volumenTotal.compareTo(volumenM3_Vehiculo) < 0) {

                System.out.println("\n============================================================================");
                System.out.println("Asignando pedido al repartidor ID: " + repartidor.getId());

                // Agregar a la lista de asignadas
                ventasAsignadas.add(rd.getVenta().getId());

                rd.setRuta(ruta);
                rutaDetRep.save(rd);

                System.out.println("Enviando notificación por WebSocket al repartidor ID: " + repartidor.getId());
                template.convertAndSend("/topic/nuevos-pedidos/" + repartidor.getId(), ruta);

                // Enviar notificación al repartidor
                this.notificacionService.create(
                        repartidor.getUsuario().getId(),
                        "🚚 - Nuevo pedido asignado",
                        "Se te ha asignado un nuevo pedido.");

                System.out.println("Proceso de asignación de ruta y repartidor finalizado.");
                System.out.println("============================================================================\n\n");
                return;
            }
            System.out.println("No hay espacio suficiente para el pedido ID: " + rd.getVenta().getId() +
                    " en el repartidor ID: " + repartidor.getId());
            rd.setRuta(ruta); // Asignar la ruta aunque no se pueda entregar
            rutaDetRep.save(rd);
        }
    }

}
