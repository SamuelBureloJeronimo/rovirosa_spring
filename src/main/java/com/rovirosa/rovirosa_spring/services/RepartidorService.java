package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.DetalleVenta.DetalleVentaResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorGetVehDTO;
import com.rovirosa.rovirosa_spring.DTOs.Repartidor.RepartidorUpdatePosDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioByRolPostDTO;
import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.DetalleVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaDetalleRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRep;
    @Autowired
    private PersonaRepository personaRep;
    @Autowired
    private UsuarioRepository usuarioRep;
    @Autowired
    private RutaDetalleRepository rutaDetalleRep;
    @Autowired
    private DetalleVentaRepository detalleServ;
    
    @Transactional
    public Integer actualizarPosicionRepartidor(Integer repartidorId, RepartidorUpdatePosDTO dto) {
        return repartidorRep.updatePosition(repartidorId, dto.getLatitud(), dto.getLongitud());
    }

    public RepartidorGetVehDTO getVehiculoByRepartidorId(Integer repartidorId) {
         RepartidorGetVehDTO repartidor = repartidorRep.findFirstById(repartidorId);
         return repartidor;
    }

    @Transactional
    public Repartidor createRepartidor(UsuarioByRolPostDTO dto) {

        Persona persona = new Persona();
        persona.setCurp(dto.getCurp());
        persona.setNombre(dto.getNombre());
        persona.setApp(dto.getApp());
        persona.setApm(dto.getApm());
        persona.setTel(dto.getTel());
        persona.setFechNac(dto.getFechaNac());
        persona.setSexo(dto.getSexo());

        persona = personaRep.save(persona);

        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setPersona(persona);

        usuario = usuarioRep.save(usuario);

        Repartidor repartidor = new Repartidor();
        repartidor.setUsuario(usuario);

        return repartidorRep.save(repartidor);
    }

    @Transactional
    public Integer asignVehiculoRepartidor(Integer repartidorId, Integer vehiculoId) {
        return repartidorRep.asignarVehiculo(repartidorId, vehiculoId);
    }

    @Transactional
    public Integer actualizarEstadoRepartidor(Integer repartidorId, String estado) {
        if (estado == null)
            return repartidorRep.updateEstadoNull(repartidorId);
        
        return repartidorRep.updateEstado(repartidorId, estado);
    }

    public Repartidor createRepartidor(Integer userId) {
        Usuario usuario = usuarioRep.findById(userId).orElse(null);
        if (usuario == null) {
            return null;
        }

        Repartidor repartidor = new Repartidor();
        repartidor.setUsuario(usuario);

        return repartidorRep.save(repartidor);
    }

    public Repartidor getRepByCurp(String curp) {
        return repartidorRep.findByUsuario_Persona_Curp(curp);
    }

    public Repartidor getRepById(Integer id) {
        return repartidorRep.findById(id).orElse(null);
    }

    public String getRepartidorToken(Integer id) {
        Repartidor repartidor = repartidorRep.findById(id).orElse(null);
        if (repartidor != null && repartidor.getUsuario() != null) {
            return repartidor.getUsuario().getTokenFmc();
        }
        return null;
    }

    public List<RutaDetalleResponseDTO> getRutaDetalleByRepartidorId(Integer repartidorId) {
        // Buscar los detalles de ruta con estado "pendiente"
        List<RutaDetalleQueryByRepartidorIdDTO> res = rutaDetalleRep.findByRuta_Repartidor_IdAndRuta_Estado(repartidorId, "en_ruta");
        // Buscar al repartidor por su id
        Repartidor rep = repartidorRep.findById(repartidorId).orElse(null);
        if (rep != null && rep.getVehiculo() != null && (rep.getEstado().equalsIgnoreCase("en_espera") || rep.getEstado().equalsIgnoreCase("cargando"))) {
            res.addAll(rutaDetalleRep.findByRuta_Repartidor_IdAndRuta_Estado(repartidorId, "pendiente"));
        }

        List<RutaDetalleResponseDTO> rutaDetalles = new ArrayList<>();
        
        for (RutaDetalleQueryByRepartidorIdDTO dto : res) {
            List<DetalleVentaResponseDTO> detalles = detalleServ.findByVenta_Id(dto.getVenta_Id());
            RutaDetalleResponseDTO rutaDetalle = new RutaDetalleResponseDTO(dto);
            rutaDetalle.setProductos(detalles);
            rutaDetalles.add(rutaDetalle);
        }

        return rutaDetalles;
    }

}
