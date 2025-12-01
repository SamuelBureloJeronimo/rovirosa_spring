package com.rovirosa.rovirosa_spring.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Auth.CreateAccount.CreateAccountDTO;
import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginResponseDTO;
import com.rovirosa.rovirosa_spring.DTOs.Cliente.ClienteQueryDireccionDTO;
import com.rovirosa.rovirosa_spring.DTOs.PuntoVenta.PuntoVentaQueryDTO;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Direccion;
import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.ClienteRepository;
import com.rovirosa.rovirosa_spring.repositories.DireccionRepository;
import com.rovirosa.rovirosa_spring.repositories.PersonaRepository;
import com.rovirosa.rovirosa_spring.repositories.PuntoVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.RepartidorRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository userRep;
    @Autowired
    private ClienteRepository clientRep;
    @Autowired
    private DireccionRepository dirRep;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PuntoVentaRepository puntoRep;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PersonaRepository personaRep;
    @Autowired
    private RepartidorRepository repartidorRep;

    public LoginResponseDTO login(LoginPostDTO loginDTO) {

        String user = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // Buscar por correo y contraseña
        LoginQueryDTO us = this.userRep.findByPasswordAndCorreo(password, user);
        if (us != null) {
            if (us.getRol().equals("CLIENTE")) {
                ClienteQueryDireccionDTO cliente = clientRep.findByUsuario_Id(us.getId());
                return new LoginResponseDTO(us, jwtUtil, cliente != null ? cliente.getId() : null);
            } else if (us.getRol().equals("REPARTIDOR")) {
                Repartidor rep = repartidorRep.findByUsuario_Id(us.getId());
                LoginResponseDTO res = new LoginResponseDTO(us, jwtUtil, null);
                res.setRepartidorId(rep.getId());
                return res;
            } else {
                return new LoginResponseDTO(us, jwtUtil, null);
            }
        }

        // Si no encuentra busca por curp
        us = this.userRep.findByPasswordAndPersona_Curp(password, user);
        if (us != null) {
            if (us.getRol().equals("CLIENTE")) {
                ClienteQueryDireccionDTO cliente = clientRep.findByUsuario_Id(us.getId());
                return new LoginResponseDTO(us, jwtUtil, cliente != null ? cliente.getId() : null);
            } else if (us.getRol().equals("REPARTIDOR")) {
                Repartidor rep = repartidorRep.findByUsuario_Id(us.getId());
                LoginResponseDTO res = new LoginResponseDTO(us, jwtUtil, null);
                res.setRepartidorId(rep.getId());
                return res;
            } else {
                return new LoginResponseDTO(us, jwtUtil, null);
            }
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<PuntoVentaQueryDTO> getAllPuntos() {
        return puntoRep.findAllProjectedBy();
    }

    @Transactional
    public String register(CreateAccountDTO dto, MultipartFile ineFront, MultipartFile ineBack) {

        Persona persona = new Persona();
        persona.setCurp(dto.getCurp());
        persona.setNombre(dto.getNombre());
        persona.setApp(dto.getApp());
        persona.setApm(dto.getApm());
        persona.setTel(dto.getTel());
        persona.setFechNac(LocalDate.parse(dto.getFechaNac()));
        persona.setSexo(dto.getSexo());

        persona = personaRep.save(persona);

        PuntoVenta punto = puntoRep.findById(dto.getPuntoVentaId()).orElse(null);

        Usuario usuario = new Usuario();
        usuario.setPersona(persona);
        usuario.setPuntoVenta(punto);
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol("CLIENTE");
        usuario.setEstado("ACTIVO");

        usuario = userRep.save(usuario);

        Direccion direccion = new Direccion();
        direccion.setLat(dto.getLat());
        direccion.setLng(dto.getLng());
        direccion.setRef(dto.getRef());

        direccion = dirRep.save(direccion);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setDireccion(direccion);
        String fileNameFront = storageService.generateFileName();
        cliente.setIneFront("ine/" + fileNameFront);
        String fileNameBack = storageService.generateFileName();
        cliente.setIneBack("ine/" + fileNameBack);

        clientRep.save(cliente);

        storageService.store(ineFront, "ine/", fileNameFront);
        storageService.store(ineBack, "ine/", fileNameBack);

        return jwtUtil.generateToken(dto.getCorreo(), "CLIENTE");
    }

    public String existUser(Usuario usuario) {
        // Buscar por correo y contraseña
        Boolean us = this.userRep.existsByCorreo(usuario.getCorreo());
        if (us)
            return "El correo proporcionado ya existe.";

        // Si no encuentra busca por curp
        us = this.userRep.existsByPersona_Curp(usuario.getPersona().getCurp());
        if (us)
            return "La curp proporcionada ya existe.";

        return "";
    }

    public Boolean existCurp(String curp) {
        // Busca por curp
        return this.userRep.existsByPersona_Curp(curp);
    }

    @Transactional
    public ApiResponse<String> updatePassword(String correo, String newPassword) {
        ApiResponse<String> response = new ApiResponse<>();
        int result = userRep.recoveyPassword(correo, newPassword);
        if (result == 0) {
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar la contraseña");
            return response;
        }
        response.setSuccess(true);
        response.setMessage("Contraseña actualizada correctamente");
        return response;
    }

    public List<String> getCoords() {
        List<PuntoVenta> puntos = puntoRep.findAll().isEmpty() ? null : puntoRep.findAll();
        return puntos != null ? puntos.stream().map(PuntoVenta::getZonaPermitida).toList() : null;

    }

}
