package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rovirosa.rovirosa_spring.DTOs.Rutas.RutaDetalleQueryByRepartidorIdDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioByRolPostDTO;
import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Usuario;
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
            return repartidor.getUsuario().getTokenFbm();
        }
        return null;
    }

    public List<RutaDetalleQueryByRepartidorIdDTO> getRutaDetalleByRepartidorId(Integer repartidorId) {
        System.out.println("Obteniendo ruta detalle para repartidor ID: " + repartidorId);
        return rutaDetalleRep.findByRuta_Repartidor_Id(repartidorId);
    }

}
