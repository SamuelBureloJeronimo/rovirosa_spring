package com.rovirosa.rovirosa_spring.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaDTO;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoDimencionesDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoQueryDimenDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaQueryClienteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaResponseClienteDTO;
import com.rovirosa.rovirosa_spring.controllers.public_routes.AuthController;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.DetalleVenta;
import com.rovirosa.rovirosa_spring.models.Pago;
import com.rovirosa.rovirosa_spring.models.Producto;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Ruta;
import com.rovirosa.rovirosa_spring.models.RutaDetalle;
import com.rovirosa.rovirosa_spring.models.Venta;
import com.rovirosa.rovirosa_spring.repositories.CatalogoPvRepository;
import com.rovirosa.rovirosa_spring.repositories.DetalleVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.PagoRepository;
import com.rovirosa.rovirosa_spring.repositories.ProductoRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorAsignacionRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaDetalleRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaRepository;
import com.rovirosa.rovirosa_spring.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {

    private final AuthController authController;

    @Autowired
    private VentaRepository ventaRep;
    @Autowired
    private DetalleVentaRepository detalleVentaRep;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PagoRepository pagoRep;
    @Autowired
    private CatalogoPvRepository catalogoPvRep;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private RutaDetalleRepository rutaDetalleRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private RepartidorAsignacionRepository repartidorAsignRep;
    @Autowired
    private ProductoRepository productoRep;

    private static final int RADIO_TIERRA_KM = 6371;
    private static final double RADIO_MAX_ENTREGA_KM = 0.36;

    VentaService(AuthController authController) {
        this.authController = authController;
    } // 360 metros

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

        if (pago.getMetodo().equalsIgnoreCase("transferencia") && comprobante != null) {
            pago.setCompr("comprobantes/" + nameCompr);
        } else {
            pago.setCompr(null);
        }

        pago = pagoRep.save(pago);

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setPuntoVenta(puntoVenta);
        venta.setPago(pago);
        venta.setFechaInicio(LocalDateTime.now().toString());

        this.crearRutaVenta(ventaDTO, venta);

        if (pago.getMetodo().equalsIgnoreCase("transferencia") && comprobante != null) {
            storageService.store(comprobante, "comprobantes/", nameCompr);
        }
    }

    public List<VentaResponseClienteDTO> getVentasByClienteId(Integer clienteId) {
        List<VentaQueryClienteDTO> ventas = ventaRep.findByCliente_Id(clienteId);
        return ventas.stream()
                .map(venta -> new VentaResponseClienteDTO(venta, detalleVentaRep.findByVenta_Id(venta.getId())))
                .toList();
    }

    private void crearRutaVenta(VentaPostDTO ventaDTO, Venta venta) {

        // Buscar repartidor en espera y cargando para el punto de venta
        List<Repartidor> repEnEspera = repartidorAsignRep.findRepartidoresActivos(ventaDTO.getPvId(),
                List.of("en_espera"));
        List<Repartidor> repCargando = repartidorAsignRep.findRepartidoresActivos(ventaDTO.getPvId(),
                List.of("cargando"));

        // Guardar la venta primero
        venta = ventaRep.save(venta);

        /*
         * Si no hay repartidores disponibles, crear una ruta sin repartidor y crear el
         * punto de parada.
         * Una RUTA es el camino completo que sigue un repartidor para entregar varios
         * pedidos.
         * Un DETALLE DE RUTA es cada uno de los puntos de parada/entrega dentro de esa
         * ruta.
         */
        if (repEnEspera.isEmpty() && repCargando.isEmpty()) {

            // Crear ruta sin repartidor
            Ruta nuevaRuta = new Ruta();
            nuevaRuta.setEstado("pendiente");
            nuevaRuta = rutaRepository.save(nuevaRuta);

            // Crear detalle de ruta para la venta pendiente
            RutaDetalle detalleRuta = new RutaDetalle();
            detalleRuta.setRuta(nuevaRuta);
            detalleRuta.setVenta(venta);
            detalleRuta.setLat(ventaDTO.getDireccion().getLat());
            detalleRuta.setLng(ventaDTO.getDireccion().getLng());
            detalleRuta.setRef(ventaDTO.getDireccion().getRef());

            rutaDetalleRepository.save(detalleRuta);
            this.registrarDetalles(ventaDTO, venta);
            return;
        }

        /*
         * YA QUE SE VALIDO ARRIBA QUE HAYA REPARTIDOR "en_espera" o "cargando".
         * 
         * 1.- Buscar los repartidores de los que estan con estado "cargando" para
         * saber si se le puede asignar el pedido si es así se le asigna y termina ahí
         * el proceso.
         * 
         */
        for (Repartidor repartidor : repCargando) {

            // Verificar que el repartidor tenga vehiculo asignado
            if (repartidor.getVehiculo() == null)
                continue;

            // Si tiene vehiculo, verificar que tenga espacio disponible
            BigDecimal capacidadKg_Vehiculo = repartidor.getVehiculo().getCapacidadKg();
            BigDecimal volumenM3_Vehiculo = repartidor.getVehiculo().getVolumenM3();

            // Obtener todos los pedidos que va entregar en camino a su ruta.
            List<RutaDetalleQueryByRepartidorIdDTO> rutasActivas = rutaDetalleRepository
                    .findByRuta_Repartidor_Id(repartidor.getId());

            // Verificar si esta cerca de alguno de los pedidos de entrega y guarda el ID de
            // la ruta
            Boolean estaCerca = false;
            Integer rutaId = -1;

            // Dimenciones totales de los pedidos en la ruta
            ProductoDimencionesDTO dimPedidosTotal = new ProductoDimencionesDTO();

            // Evaluar si esta cerca de alguno de los pedidos de entrega
            for (RutaDetalleQueryByRepartidorIdDTO rutaAct : rutasActivas) {

                // Verificar si puede entregar en la direccion de la venta nueva
                if (this.puedeEntregar(rutaAct.getLat(), rutaAct.getLng(), ventaDTO.getDireccion().getLat(),
                        ventaDTO.getDireccion().getLng())) {
                    estaCerca = true;
                    rutaId = rutaAct.getId();
                }
                // Aunque no este cerca debe seguir sumando todos los pedidos que tiene
                List<DetalleVentaResponseDTO> ventasDetalles = detalleVentaRep
                        .findByVenta_Id(rutaAct.getVenta_Id());
                // Sumar las dimenciones de cada pedido en la ruta
                for (DetalleVentaResponseDTO detVen : ventasDetalles) {
                    dimPedidosTotal.setPesoKg(
                            dimPedidosTotal.getPesoKg().add(
                                    BigDecimal.valueOf(detVen.getProducto_PesoKg())
                                            .multiply(BigDecimal.valueOf(detVen.getCantIn()))));
                    dimPedidosTotal.setVolM3(
                            dimPedidosTotal.getVolM3().add(
                                    BigDecimal.valueOf(detVen.getProducto_VolM3())
                                            .multiply(BigDecimal.valueOf(detVen.getCantIn()))));

                }

            }
            // Si encuentra algun pedido cercano en la ruta del repartidor
            if (estaCerca && rutaId != -1) {

                // Obtener la dimeciones del pedido
                ProductoDimencionesDTO dim = this.getTotalKgAndVolumen(ventaDTO, venta);

                BigDecimal pesoTotal = dim.getPesoKg().add(dimPedidosTotal.getPesoKg());
                BigDecimal volumenTotal = dim.getVolM3().add(dimPedidosTotal.getVolM3());

                // Validar que tenga espacio
                if (pesoTotal.compareTo(capacidadKg_Vehiculo) < 0 && volumenTotal.compareTo(volumenM3_Vehiculo) < 0) {
                    // Seleccionar la ruta activa
                    Ruta ruta = new Ruta();
                    ruta.setId(rutaId);
                    // Nuevo punto de entrega en la ruta
                    RutaDetalle detalleRuta = new RutaDetalle();
                    detalleRuta.setRuta(ruta);
                    detalleRuta.setVenta(venta);
                    detalleRuta.setLat(ventaDTO.getDireccion().getLat());
                    detalleRuta.setLng(ventaDTO.getDireccion().getLng());
                    detalleRuta.setRef(ventaDTO.getDireccion().getRef());
                    rutaDetalleRepository.save(detalleRuta);
                    this.registrarDetalles(ventaDTO, venta);
                    return;
                }

            }
        }

        /*
         * 2.- Si no se le puede asignar el pedido ya sea porque esta lejos o porque no
         * tiene el espacio suficiente busca ahora uno "en_espera"
         */
        for (Repartidor repartidor : repEnEspera) {

            System.out.println("Evaluando repartidor en espera: " + repartidor.getUsuario().getPersona().getNombre());

            // Verificar que el repartidor tenga vehiculo asignado
            if (repartidor.getVehiculo() == null)
                continue;

            // Si tiene vehiculo, verificar que tenga espacio disponible
            BigDecimal capacidadKg_Vehiculo = repartidor.getVehiculo().getCapacidadKg();
            BigDecimal volumenM3_Vehiculo = repartidor.getVehiculo().getVolumenM3();

            System.out.println("Capacidad Kg: " + capacidadKg_Vehiculo + " Volumen M3: " + volumenM3_Vehiculo);

            // Obtener la dimeciones del pedido
            ProductoDimencionesDTO dim = this.getTotalKgAndVolumen(ventaDTO, venta);

            System.out.println("Dimensiones pedido - Peso Kg: " + dim.getPesoKg() + " Volumen M3: " + dim.getVolM3());

            BigDecimal pesoPedido = dim.getPesoKg();
            BigDecimal volPedido = dim.getVolM3();

            // Validar que tenga espacio
            if (pesoPedido.compareTo(capacidadKg_Vehiculo) < 0 && volPedido.compareTo(volumenM3_Vehiculo) < 0) {

                System.out.println("Asignando al repartidor en espera ID: " + repartidor.getId());

                // Crear ruta sin repartidor
                Ruta nuevaRuta = new Ruta();
                nuevaRuta.setEstado("pendiente");
                nuevaRuta.setRepartidor(repartidor);
                nuevaRuta = rutaRepository.save(nuevaRuta);

                System.out.println("Ruta creada ID: " + nuevaRuta.getId());

                // Nuevo punto de entrega en la ruta
                RutaDetalle detalleRuta = new RutaDetalle();
                detalleRuta.setRuta(nuevaRuta);
                detalleRuta.setVenta(venta);
                detalleRuta.setLat(ventaDTO.getDireccion().getLat());
                detalleRuta.setLng(ventaDTO.getDireccion().getLng());
                detalleRuta.setRef(ventaDTO.getDireccion().getRef());
                rutaDetalleRepository.save(detalleRuta);

                System.out.println("Detalle de ruta creado para la venta ID: " + venta.getId());

                this.registrarDetalles(ventaDTO, venta);
                return;
            }

        }

    }

    private ProductoDimencionesDTO getTotalKgAndVolumen(VentaPostDTO ventaDTO, Venta venta) {
        ProductoDimencionesDTO dim = new ProductoDimencionesDTO(BigDecimal.ZERO, BigDecimal.ZERO);

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetallesVenta()) {
            ProductoQueryDimenDTO producto = productoRep.findProductoQueryDimenDTOById(detalleDTO.getProductoId());
            if (producto == null)
                continue;

            System.out.println("Producto ID: " + detalleDTO.getProductoId() +
                    " Peso Kg: " + producto.getPesoKg() + " Volumen M3: " + producto.getVolM3());

            // Multiplicar cantidad × peso
            BigDecimal cantidad = BigDecimal.valueOf(detalleDTO.getCantidad());
            BigDecimal pesoProducto = producto.getPesoKg().multiply(cantidad);
            BigDecimal volProducto = producto.getVolM3().multiply(cantidad);

            // Acumular
            dim.setPesoKg(dim.getPesoKg().add(pesoProducto));
            dim.setVolM3(dim.getVolM3().add(volProducto));
        }

        return dim;
    }

    private void registrarDetalles(VentaPostDTO ventaDTO, Venta venta) {
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetallesVenta()) {
            Producto producto = new Producto();
            producto.setId(detalleDTO.getProductoId());

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantIn(detalleDTO.getCantidad());
            detalle.setPrecioUnit(detalleDTO.getPrecioUnitario());
            detalle.setDescUnit(detalleDTO.getDescuentoUnitario());

            CatalogoQueryDTO cat = this.catalogoPvRep.findFirstCatalogoQueryDTOByProducto_IdAndPuntoVenta_Id(
                    detalleDTO.getProductoId(), ventaDTO.getPvId());
            catalogoPvRep.updateStock(ventaDTO.getPvId(), detalleDTO.getProductoId(),
                    cat.getStock() - detalle.getCantIn());

            detalleVentaRep.save(detalle);
        }
    }

    private boolean puedeEntregar(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double distancia = calcularDistancia(lat1, lon1, lat2, lon2);
        return distancia <= RADIO_MAX_ENTREGA_KM;
    }

    private double calcularDistancia(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double latDistance = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
        double lonDistance = Math.toRadians(lon2.doubleValue() - lon1.doubleValue());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1.doubleValue())) * Math.cos(Math.toRadians(lat2.doubleValue()))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }

}
