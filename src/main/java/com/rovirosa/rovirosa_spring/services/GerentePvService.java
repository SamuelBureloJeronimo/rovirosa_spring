package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioByRolPostDTO;
import com.rovirosa.rovirosa_spring.models.GerentePv;
import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.GerentePvRepository;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class GerentePvService {

    @Autowired
    private GerentePvRepository gerenteRep;
    @Autowired
    private PersonaRepository personaRep;
    @Autowired
    private UsuarioRepository usuarioRep;

    public GerentePvQueryDTO getGerentePvById(String curp) {
        return gerenteRep.findByUsuario_Persona_Curp(curp);
    }

    public List<GerentePvQueryDTO> getAllGerentesPv() {
        return gerenteRep.findAllProjectedBy();
    }

    @Transactional
    public GerentePv createGerentePv(UsuarioByRolPostDTO dto) {

        Persona persona = new Persona();
        persona.setCurp(dto.getCurp());
        persona.setNombre(dto.getNombre());
        persona.setApp(dto.getApp());
        persona.setApm(dto.getApm());
        persona.setTel(dto.getTel());
        persona.setFechNac(dto.getFechaNac());
        persona.setSexo(dto.getSexo());

        persona = personaRep.save(persona);

        PuntoVenta pv = new PuntoVenta();
        pv.setId(dto.getPv_id());

        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setPuntoVenta(pv);
        usuario.setPersona(persona);

        usuario = usuarioRep.save(usuario);


        GerentePv gerente = new GerentePv();
        gerente.setUsuario(usuario);
        gerente.setPuntoVenta(pv);
        

        return gerenteRep.save(gerente);

    }

    @Transactional
    public void assignPuntoVentaToGerente(Integer gerenteId, Integer pvId) {
        GerentePv gerente = gerenteRep.findById(gerenteId)
                .orElseThrow(() -> new RuntimeException("GerentePv not found with id: " + gerenteId));
        PuntoVenta pv = new PuntoVenta();
        pv.setId(pvId);
        gerente.setPuntoVenta(pv);
        gerenteRep.save(gerente);
    }
    

    @Transactional
    public void removePuntoVentaFromGerente(Integer idGerente) {
        GerentePv gerente = gerenteRep.findById(idGerente)
                .orElseThrow(() -> new RuntimeException("GerentePv not found with id: " + idGerente));
        gerente.setPuntoVenta(null);
        gerenteRep.save(gerente);
    }

}
