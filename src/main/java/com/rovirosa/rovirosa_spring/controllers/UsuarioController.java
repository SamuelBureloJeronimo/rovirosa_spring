package com.rovirosa.rovirosa_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Notificaciones.NotificacionDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.ChangeCorreoDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.ChangePassDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.MyPerfilDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioByRolPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioSimpleResponseDTO;
import com.rovirosa.rovirosa_spring.models.GerentePv;
import com.rovirosa.rovirosa_spring.models.Repartidor;
import com.rovirosa.rovirosa_spring.services.GerentePvService;
import com.rovirosa.rovirosa_spring.services.NotificacionService;
import com.rovirosa.rovirosa_spring.services.RepartidorService;
import com.rovirosa.rovirosa_spring.services.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private RepartidorService repartidorService;
    @Autowired
    private GerentePvService gerentePvService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-by-rol")
    public ResponseEntity<ApiResponse<UsuarioSimpleResponseDTO>> registerUserByRol(
            @RequestBody UsuarioByRolPostDTO dto) {

        if (dto.getRol().equals("REPARTIDOR")) {
            Repartidor repartidor = repartidorService.createRepartidor(dto);

            if (repartidor == null)
                return ResponseEntity.ok(new ApiResponse<>(false, "Error al crear repartidor", null));

            UsuarioSimpleResponseDTO repartidorDTO = new UsuarioSimpleResponseDTO(
                    repartidor.getUsuario().getId(),
                    repartidor.getUsuario().getCorreo(),
                    repartidor.getUsuario().getEstado(),
                    repartidor.getUsuario().getRol(),
                    repartidor.getUsuario().getPersona());

            return ResponseEntity.ok(new ApiResponse<>(true, "Repartidor creado exitosamente", repartidorDTO));
        } else if (dto.getRol().equals("GERENTE")) {
            GerentePv repartidor = gerentePvService.createGerentePv(dto);

            if (repartidor == null)
                return ResponseEntity.ok(new ApiResponse<>(false, "Error al crear gerente", null));

            UsuarioSimpleResponseDTO repartidorDTO = new UsuarioSimpleResponseDTO(
                    repartidor.getUsuario().getId(),
                    repartidor.getUsuario().getCorreo(),
                    repartidor.getUsuario().getEstado(),
                    repartidor.getUsuario().getRol(),
                    repartidor.getUsuario().getPersona());

            return ResponseEntity.ok(new ApiResponse<>(true, "Gerente creado exitosamente", repartidorDTO));
        }
        return ResponseEntity.status(400).body(
                new ApiResponse<>(false, "Rol no soportado para este endpoint", null));
    }

    @PutMapping("/update-correo")
    public ResponseEntity<ApiResponse<Void>> changeEmail(@RequestBody ChangeCorreoDTO usuarioDto) {
        ApiResponse<Void> res = usuarioService.changeCorreo(usuarioDto.getId(), usuarioDto.getNuevoCorreo());
        if (res.isSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(404).body(res);
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody ChangePassDTO usuarioDto) {
        ApiResponse<Void> res = usuarioService.changePassword(usuarioDto.getId(), usuarioDto.getNewPass(),
                usuarioDto.getActualPass());
        if (res.isSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(404).body(res);
        }
    }

    @GetMapping("/notificaciones/{userId}")
    public ResponseEntity<ApiResponse<List<NotificacionDTO>>> getNotificaciones(@PathVariable Integer userId) {
        ApiResponse<List<NotificacionDTO>> res = notificacionService.getNotificaciones(userId);
        if (res.isSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(404).body(res);
        }
    }

    @PutMapping("/leer-notifiacion/{id}")
    public ResponseEntity<ApiResponse<Void>> leerNotificacion(@PathVariable Integer id) {
        ApiResponse<Void> res = notificacionService.leerNotificacion(id);
        if (res.isSuccess())
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación marcada como leída", null));
        else
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Notificación no encontrada", null));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-status/{id}/{estado}")
    public ResponseEntity<ApiResponse<String>> changeStatus(@PathVariable Integer id, @PathVariable String estado) {
        int rowsAffected = usuarioService.changeStatus(id, estado);
        if (rowsAffected > 0) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado exitosamente", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Usuario no encontrado", null));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/{rol}")
    public ResponseEntity<ApiResponse<List<UsuarioSimpleResponseDTO>>> getUsuariosByRol(@PathVariable String rol) {
        List<UsuarioSimpleResponseDTO> usuarios = usuarioService.getUsuariosByRol(rol);
        ApiResponse<List<UsuarioSimpleResponseDTO>> response = new ApiResponse<>(true,
                "Usuarios obtenidos exitosamente", usuarios);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @PutMapping("/update-pv/{user_id}/{pv_id}")
    public ResponseEntity<ApiResponse<UsuarioQueryDTO>> updatePv(@PathVariable Integer pv_id,
            @PathVariable Integer user_id) {
        usuarioService.updatePuntoDeVenta(pv_id, user_id);
        ApiResponse<UsuarioQueryDTO> response = new ApiResponse<>(true, "Punto de venta actualizado exitosamente",
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<ApiResponse<MyPerfilDTO>> getPerfil(
            @PathVariable Integer id) {
        ApiResponse<MyPerfilDTO> perfil = usuarioService.getPerfilById(id);
        if (!perfil.isSuccess())
            return ResponseEntity.status(404).body(perfil);
        return ResponseEntity.ok(perfil);
    }

}
