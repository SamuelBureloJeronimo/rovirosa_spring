package com.rovirosa.rovirosa_spring.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.Catalogo.CatalogoQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaDTO;
import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoDimencionesDTO;
import com.rovirosa.rovirosa_spring.DTOs.Producto.ProductoQueryDimenDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaQueryClienteDTO;
import com.rovirosa.rovirosa_spring.DTOs.Venta.VentaResponseClienteDTO;
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
        private RepartidorAsignacionRepository repartidorAsignRep;
        @Autowired
        private ProductoRepository productoRep;
        @Autowired
        private FirebaseService firebaseService;
        @Autowired
        private RepartidorRepository repartidorRep;

        @Autowired
        private SimpMessagingTemplate template;

        private static final int RADIO_TIERRA_KM = 6371;
        private static final double RADIO_MAX_ENTREGA_KM = 0.36;

        public static final String RESET = "\u001B[0m";
        public static final String ROJO = "\u001B[31m";
        public static final String VERDE = "\u001B[32m";
        public static final String AMARILLO = "\u001B[33m";
        public static final String AZUL = "\u001B[34m";
        public static final String MORADO = "\u001B[35m";
        public static final String CIAN = "\u001B[36m";

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
                                .map(venta -> new VentaResponseClienteDTO(venta,
                                                detalleVentaRep.findByVenta_Id(venta.getId())))
                                .toList();
        }

        private void crearRutaVenta(VentaPostDTO ventaDTO, Venta venta) {

                System.out.println("\n\n============================================================================");
                System.out.println(AZUL + "Iniciando proceso de asignación de ruta y repartidor..." + RESET);

                // Buscar repartidor en espera y cargando para el punto de venta
                List<Repartidor> repEnEspera = repartidorAsignRep.findRepartidoresActivos(ventaDTO.getPvId(),
                                List.of("en_espera"));
                List<Repartidor> repCargando = repartidorAsignRep.findRepartidoresActivos(ventaDTO.getPvId(),
                                List.of("cargando"));

                // Guardar la venta primero
                venta = ventaRep.save(venta);
                System.out.println(VERDE + "Venta creada con ID: " + venta.getId() + RESET);

                /*
                 * Si no hay repartidores disponibles, crear una ruta sin repartidor y crear el
                 * punto de parada.
                 * Una RUTA es el camino completo que sigue un repartidor para entregar varios
                 * pedidos.
                 * Un DETALLE DE RUTA es cada uno de los puntos de parada/entrega dentro de esa
                 * ruta.
                 */
                System.out.println(AMARILLO + "Validando si hay repartidores disponibles..." + RESET);
                if (repEnEspera.isEmpty() && repCargando.isEmpty()) {

                        System.out.println(AMARILLO + "No hay repartidores disponibles, creando ruta sin repartidor."
                                        + RESET);
                        // Crear ruta sin repartidor
                        Ruta nuevaRuta = new Ruta();
                        PuntoVenta pv = new PuntoVenta();
                        pv.setId(ventaDTO.getPvId());
                        nuevaRuta.setPuntoVenta(pv);
                        nuevaRuta.setEstado("pendiente");
                        nuevaRuta = rutaRepository.save(nuevaRuta);

                        this.createRutaDetalleVenta(nuevaRuta, ventaDTO, venta);

                        System.out.println(AZUL + "Proceso de asignación de ruta y repartidor finalizado." + RESET);
                        System.out.println(
                                        "============================================================================\n\n");

                        return;
                }
                System.out.println(VERDE + "Repartidores disponibles encontrados." + RESET);

                /*
                 * YA QUE SE VALIDO ARRIBA QUE HAYA REPARTIDOR "en_espera" o "cargando".
                 * 
                 * 1.- Buscar los repartidores de los que estan con estado "cargando" para
                 * saber si se le puede asignar el pedido si es así se le asigna y termina ahí
                 * el proceso.
                 * 
                 */
                System.out.println(AMARILLO + "Buscando entre los repartidores 'cargando'..." + RESET);
                for (Repartidor repartidor : repCargando) {

                        System.out.println(AZUL + "Evaluando repartidor cargando: "
                                        + repartidor.getUsuario().getPersona().getNombre() + RESET);

                        // Verificar que el repartidor tenga vehiculo asignado
                        if (repartidor.getVehiculo() == null) {
                                System.out.println(ROJO + "Repartidor no tiene vehiculo asignado, saltando..." + RESET);
                                continue;
                        }

                        // Si tiene vehiculo, verificar que tenga espacio disponible
                        BigDecimal capacidadKg_Vehiculo = repartidor.getVehiculo().getCapacidadKg();
                        BigDecimal volumenM3_Vehiculo = repartidor.getVehiculo().getVolumenM3();
                        capacidadKg_Vehiculo = capacidadKg_Vehiculo
                                        .multiply(BigDecimal.valueOf(repartidor.getVehiculo().getFactorUsoMax()));
                        volumenM3_Vehiculo = volumenM3_Vehiculo
                                        .multiply(BigDecimal.valueOf(repartidor.getVehiculo().getFactorUsoMax()));

                        System.out.println(
                                        AZUL + "[VEHICULO]: Capacidad Kg: " + capacidadKg_Vehiculo + " Volumen M3: "
                                                        + volumenM3_Vehiculo + " Factor de uso MAX: "
                                                        + repartidor.getVehiculo().getFactorUsoMax() + RESET);

                        // Obtener todos los pedidos que va entregar en camino a su ruta.
                        List<RutaDetalleQueryByRepartidorIdDTO> rutasActivas = rutaDetalleRepository
                                        .findByRuta_Repartidor_Id(repartidor.getId());

                        // Verificar si esta cerca de alguno de los pedidos de entrega y guarda el ID de
                        // la ruta
                        Boolean estaCerca = false;
                        Integer rutaId = -1;

                        // Dimenciones totales de los pedidos en la ruta
                        Double pesoTotal = 0.0;
                        Double volTotal = 0.0;

                        System.out.println(AMARILLO + "Evaluando POINTS en las rutas activas del repartidor ID: "
                                        + repartidor.getId() +
                                        " Cantidad de POINTS en rutas activas: " + rutasActivas.size() + RESET);

                        // Evaluar si esta cerca de alguno de los pedidos de entrega
                        for (RutaDetalleQueryByRepartidorIdDTO rutaAct : rutasActivas) {

                                // Verificar si puede entregar en la direccion de la venta nueva
                                if (this.puedeEntregar(rutaAct.getLat(), rutaAct.getLng(),
                                                ventaDTO.getDireccion().getLat(),
                                                ventaDTO.getDireccion().getLng())) {
                                        System.out.println(AZUL + "Repartidor "
                                                        + repartidor.getUsuario().getPersona().getNombre() +
                                                        " esta cerca de la nueva venta en el POINT ID: "
                                                        + rutaAct.getId() + RESET);
                                        estaCerca = true;
                                        rutaId = rutaAct.getRuta_Id();
                                }
                                // Aunque no este cerca debe seguir sumando todos los pedidos que tiene
                                List<DetalleVentaResponseDTO> ventasDetalles = detalleVentaRep
                                                .findByVenta_Id(rutaAct.getVenta_Id());

                                System.out.println(AZUL + "Sumando dimensiones de venta ID: " + rutaAct.getVenta_Id() +
                                                " Cantidad de ventas: " + ventasDetalles.size() + RESET);

                                // Sumar las dimenciones de cada pedido en la ruta
                                for (DetalleVentaResponseDTO detVen : ventasDetalles) {
                                        // Multiplicar cantidad × peso y volumen
                                        pesoTotal += detVen.getProducto_PesoKg() * detVen.getCantIn();
                                        volTotal += detVen.getProducto_VolM3() * detVen.getCantIn();
                                }
                                System.out.println(AZUL +
                                                "Dimensiones acumuladas hasta ahora - Peso Kg: " + pesoTotal
                                                + " Volumen M3: " + volTotal
                                                + RESET);
                        }

                        System.out.println(
                                        AZUL + "Dimensiones totales en ruta - Peso Kg: " + pesoTotal + " Volumen M3: "
                                                        + volTotal + RESET);
                        // Si encuentra algun pedido cercano en la ruta del repartidor
                        if (estaCerca && rutaId != -1) {

                                // Obtener la dimeciones del pedido
                                ProductoDimencionesDTO dim = this.getTotalKgAndVolumen(ventaDTO, venta);

                                System.out
                                                .println(AZUL + "Dimensiones pedido - Peso Kg: " + dim.getPesoKg()
                                                                + " Volumen M3: "
                                                                + dim.getVolM3() + RESET);

                                BigDecimal pesoTotalPedido = dim.getPesoKg().add(BigDecimal.valueOf(pesoTotal));
                                BigDecimal volumenTotalPedido = dim.getVolM3().add(BigDecimal.valueOf(volTotal));

                                System.out
                                                .println(AZUL + "Dimensiones totales con nuevo pedido - Peso Kg: "
                                                                + pesoTotalPedido
                                                                + " Volumen M3: "
                                                                + volumenTotalPedido + RESET);

                                // Validar que tenga espacio
                                if (pesoTotalPedido.compareTo(capacidadKg_Vehiculo) < 0
                                                && volumenTotalPedido.compareTo(volumenM3_Vehiculo) < 0) {

                                        System.out.println(VERDE + "Asignando al repartidor cargando ID: "
                                                        + repartidor.getId() + RESET);
                                        System.out.println(VERDE + "Ruta seleccionada ID: " + rutaId + RESET);
                                        System.out.println(VERDE
                                                        + "Creando nuevo punto de entrega en la ruta para la venta ID: "
                                                        + venta.getId() + RESET);
                                        // Seleccionar la ruta activa
                                        Ruta ruta = new Ruta();
                                        PuntoVenta pv = new PuntoVenta();
                                        pv.setId(ventaDTO.getPvId());
                                        ruta.setPuntoVenta(pv);
                                        ruta.setId(rutaId);
                                        // Nuevo punto de entrega en la ruta
                                        Integer idRutaDetalle = this.createRutaDetalleVenta(ruta, ventaDTO, venta);

                                        repartidor.setEstado("cargando");
                                        repartidorRep.save(repartidor);

                                        RutaDetalleQueryByRepartidorIdDTO rutaDetalle = rutaDetalleRepository
                                                        .findFirstById(idRutaDetalle);

                                        RutaDetalleResponseDTO rutaRes = new RutaDetalleResponseDTO(rutaDetalle);

                                        List<DetalleVentaResponseDTO> detalles = detalleVentaRep
                                                        .findByVenta_Id(venta.getId());
                                        rutaRes.setProductos(detalles);

                                        System.out.println(
                                                        CIAN + "Enviando notificación por WebSocket al repartidor ID: "
                                                                        + repartidor.getId() + RESET);
                                        template.convertAndSend("/topic/nuevos-pedidos/" + repartidor.getId(),
                                                        rutaRes);

                                        // Enviar notificación al repartidor
                                        String tokenFMC = repartidor.getUsuario().getTokenFmc();
                                        if (tokenFMC != null && !tokenFMC.isEmpty()) {
                                                System.out.println(
                                                                AMARILLO + "Token FCM repartidor: " + tokenFMC + RESET);
                                                try {
                                                        this.firebaseService.sendNotification(tokenFMC,
                                                                        "🚚 - Nuevo pedido asignado",
                                                                        "Se te ha asignado un nuevo pedido cerca de tu ruta actual.");
                                                } catch (Exception e) {
                                                        System.out.println(ROJO + "Error enviando notificación FCM: "
                                                                        + e.getMessage()
                                                                        + RESET);
                                                }
                                        } else {
                                                System.out.println(ROJO
                                                                + "El repartidor no tiene token FCM, no se puede enviar notificación."
                                                                + RESET);
                                        }
                                        System.out.println("Token FCM repartidor: " + tokenFMC);
                                        this.firebaseService.sendNotification(tokenFMC, "🚚 - Nuevo pedido asignado",
                                                        "Se te ha asignado un nuevo pedido cerca de tu ruta actual.");
                                        System.out.println(
                                                        AZUL + "Proceso de asignación de ruta y repartidor finalizado."
                                                                        + RESET);
                                        System.out.println(
                                                        "============================================================================\n\n");
                                        return;
                                }
                                System.out.println(AZUL + "Dimensiones del pedido exceden la capacidad del vehiculo."
                                                + RESET);

                        }
                        System.out.println(ROJO + "Repartidor no tiene espacio suficiente o no esta cerca, saltando..."
                                        + RESET);
                }

                System.out.println(
                                ROJO + "No se pudo asignar a ningun repartidor cargando, buscando entre los en espera."
                                                + RESET);

                /*
                 * 2.- Si no se le puede asignar el pedido ya sea porque esta lejos o porque no
                 * tiene el espacio suficiente busca ahora uno "en_espera"
                 */
                for (Repartidor repartidor : repEnEspera) {

                        System.out.println(AMARILLO + "Evaluando repartidor en espera: "
                                        + repartidor.getUsuario().getPersona().getNombre() + RESET);

                        // Verificar que el repartidor tenga vehiculo asignado
                        if (repartidor.getVehiculo() == null)
                                continue;

                        // Si tiene vehiculo, verificar que tenga espacio disponible
                        BigDecimal capacidadKg_Vehiculo = repartidor.getVehiculo().getCapacidadKg();
                        BigDecimal volumenM3_Vehiculo = repartidor.getVehiculo().getVolumenM3();

                        System.out.println(
                                        AZUL + "Capacidad Kg: " + capacidadKg_Vehiculo + " Volumen M3: "
                                                        + volumenM3_Vehiculo + RESET);

                        // Obtener la dimeciones del pedido
                        ProductoDimencionesDTO dim = this.getTotalKgAndVolumen(ventaDTO, venta);

                        System.out.println(AZUL + "Dimensiones pedido - Peso Kg: " + dim.getPesoKg() + " Volumen M3: "
                                        + dim.getVolM3() + RESET);

                        BigDecimal pesoPedido = dim.getPesoKg();
                        BigDecimal volPedido = dim.getVolM3();

                        // Validar que tenga espacio
                        if (pesoPedido.compareTo(capacidadKg_Vehiculo) < 0
                                        && volPedido.compareTo(volumenM3_Vehiculo) < 0) {

                                System.out.println(VERDE + "Asignando al repartidor en espera ID: " + repartidor.getId()
                                                + RESET);

                                // Crear ruta sin repartidor
                                Ruta nuevaRuta = new Ruta();
                                PuntoVenta pv = new PuntoVenta();
                                pv.setId(ventaDTO.getPvId());
                                nuevaRuta.setPuntoVenta(pv);
                                nuevaRuta.setEstado("pendiente");
                                nuevaRuta.setRepartidor(repartidor);
                                nuevaRuta = rutaRepository.save(nuevaRuta);

                                System.out.println(VERDE + "Ruta creada ID: " + nuevaRuta.getId() + RESET);

                                // Nuevo punto de entrega en la ruta
                                RutaDetalle detalleRuta = new RutaDetalle();
                                detalleRuta.setRuta(nuevaRuta);
                                detalleRuta.setVenta(venta);
                                detalleRuta.setLat(ventaDTO.getDireccion().getLat());
                                detalleRuta.setLng(ventaDTO.getDireccion().getLng());
                                detalleRuta.setRef(ventaDTO.getDireccion().getRef());
                                detalleRuta = rutaDetalleRepository.save(detalleRuta);

                                repartidor.setEstado("cargando");
                                repartidorRep.save(repartidor);

                                System.out.println(VERDE + "Detalle de ruta creado para la venta ID: " + venta.getId()
                                                + RESET);

                                this.registrarDetalles(ventaDTO, venta);

                                RutaDetalleQueryByRepartidorIdDTO rutaDetalle = rutaDetalleRepository
                                                .findFirstById(detalleRuta.getId());

                                RutaDetalleResponseDTO rutaRes = new RutaDetalleResponseDTO(rutaDetalle);

                                List<DetalleVentaResponseDTO> detalles = detalleVentaRep
                                                .findByVenta_Id(venta.getId());
                                rutaRes.setProductos(detalles);

                                System.out.println(
                                                CIAN + "Enviando notificación por WebSocket al repartidor ID: "
                                                                + repartidor.getId() + RESET);
                                template.convertAndSend("/topic/nuevos-pedidos/" + repartidor.getId(), rutaRes);

                                // Enviar notificación al repartidor
                                String tokenFMC = repartidor.getUsuario().getTokenFmc();
                                if (tokenFMC != null && !tokenFMC.isEmpty()) {
                                        System.out.println(AMARILLO + "Token FCM repartidor: " + tokenFMC + RESET);
                                        try {
                                                this.firebaseService.sendNotification(tokenFMC,
                                                                "🚚 - Nuevo pedido asignado",
                                                                "Se te ha asignado un nuevo pedido cerca de tu ruta actual.");
                                        } catch (Exception e) {
                                                System.out.println(ROJO + "Error enviando notificación FCM: "
                                                                + e.getMessage()
                                                                + RESET);
                                        }
                                }

                                System.out.println(AZUL + "Proceso de asignación de ruta y repartidor finalizado."
                                                + RESET);
                                System.out.println(
                                                "============================================================================\n\n");
                                return;
                        }

                }

                System.out.println(
                                ROJO + "No se pudo asignar el pedido a ningun repartidor, creando ruta sin repartidor."
                                                + RESET);
                // Crear ruta sin repartidor
                Ruta nuevaRuta = new Ruta();
                PuntoVenta pv = new PuntoVenta();
                pv.setId(ventaDTO.getPvId());
                nuevaRuta.setPuntoVenta(pv);
                nuevaRuta.setEstado("pendiente");
                nuevaRuta = rutaRepository.save(nuevaRuta);

                this.createRutaDetalleVenta(nuevaRuta, ventaDTO, venta);

                System.out.println(AZUL + "Proceso de asignación de ruta y repartidor finalizado." + RESET);
                System.out.println("============================================================================\n\n");
                return;

        }

        private Integer createRutaDetalleVenta(Ruta nuevaRuta, VentaPostDTO ventaDTO, Venta venta) {

                // Crear detalle de ruta para la venta pendiente
                RutaDetalle detalleRuta = new RutaDetalle();
                detalleRuta.setRuta(nuevaRuta);
                detalleRuta.setVenta(venta);
                detalleRuta.setLat(ventaDTO.getDireccion().getLat());
                detalleRuta.setLng(ventaDTO.getDireccion().getLng());
                detalleRuta.setRef(ventaDTO.getDireccion().getRef());

                this.registrarDetalles(ventaDTO, venta);
                detalleRuta = rutaDetalleRepository.save(detalleRuta);
                return detalleRuta.getId();
        }

        private ProductoDimencionesDTO getTotalKgAndVolumen(VentaPostDTO ventaDTO, Venta venta) {
                ProductoDimencionesDTO dim = new ProductoDimencionesDTO(BigDecimal.ZERO, BigDecimal.ZERO);

                for (DetalleVentaDTO detalleDTO : ventaDTO.getDetallesVenta()) {
                        ProductoQueryDimenDTO producto = productoRep
                                        .findProductoQueryDimenDTOById(detalleDTO.getProductoId());
                        if (producto == null)
                                continue;

                        System.out.println(AZUL + "Producto ID: " + detalleDTO.getProductoId() +
                                        " Peso Kg: " + producto.getPesoKg() + " Volumen M3: " + producto.getVolM3()
                                        + RESET);

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

                        CatalogoQueryDTO cat = this.catalogoPvRep
                                        .findFirstCatalogoQueryDTOByProducto_IdAndPuntoVenta_Id(
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
                                + Math.cos(Math.toRadians(lat1.doubleValue()))
                                                * Math.cos(Math.toRadians(lat2.doubleValue()))
                                                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                return RADIO_TIERRA_KM * c;
        }

}
