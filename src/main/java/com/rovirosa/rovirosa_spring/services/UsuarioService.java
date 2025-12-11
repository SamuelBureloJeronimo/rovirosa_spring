package com.rovirosa.rovirosa_spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.MyPerfilDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioSimpleResponseDTO;
import com.rovirosa.rovirosa_spring.models.PuntoVenta;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IUsuario;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements IUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CarritoService carritoService;

    public List<UsuarioSimpleResponseDTO> getUsuariosByRol(String rol) {

        List<UsuarioQueryDTO> usuarios = usuarioRepository.findByRol(rol);
        List<UsuarioSimpleResponseDTO> result = new ArrayList<>();
        
        for (UsuarioQueryDTO usuarioQueryDTO : usuarios) {
            result.add(new UsuarioSimpleResponseDTO(usuarioQueryDTO));
        }
        
        return result;

    }

    @Transactional
    public int changeStatus(Integer userId, String estado) {
        return usuarioRepository.changeStatus(userId, estado);
    }

    @Transactional
    public void updatePuntoDeVenta(Integer pvId, Integer userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PuntoVenta pvRef = entityManager.getReference(PuntoVenta.class, pvId);
        usuario.setPuntoVenta(pvRef);
        carritoService.clearCartByUserId(userId);
        usuarioRepository.save(usuario);
    }

    @Override
    public ApiResponse<MyPerfilDTO> getPerfilById(Integer id) {
        MyPerfilDTO perfil = this.usuarioRepository.findPerfilById(id);
        if (perfil != null) {
            return new ApiResponse<>(true, "Perfil found", perfil);
        } else {
            return new ApiResponse<>(false, "Perfil not found", null);
        }

    }

    @Transactional
    @Override
    public ApiResponse<Void> changeCorreo(Integer id, String nuevoCorreo) {

        boolean correoExists = usuarioRepository.existsByCorreo(nuevoCorreo);
        if (correoExists) {
            return new ApiResponse<>(false, "El correo ya está en uso", null);
        }

        int rowAffected = usuarioRepository.changeCorreo(id, nuevoCorreo);

        if (rowAffected > 0)
            return new ApiResponse<>(true, "Correo actualizado exitosamente", null);
        
        return new ApiResponse<>(false, "Usuario no encontrado", null);
        
    }

    @Transactional
    @Override
    public ApiResponse<Void> changePassword(Integer id, String nuevaPassword, String actualPass) {
        
        int rowAffected = usuarioRepository.changePassword(id, nuevaPassword, actualPass);

        if (rowAffected > 0)
            return new ApiResponse<>(true, "Contraseña actualizada exitosamente", null);
        
        return new ApiResponse<>(false, "El usuario no existe o la contraseña actual no es la correcta.", null);

    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteUsuario(Integer id) {
        int rowsAffected = usuarioRepository.deleteUsuarioById(id);
        if (rowsAffected > 0) {
            return new ApiResponse<>(true, "Usuario eliminado exitosamente", null);
        } else {
            return new ApiResponse<>(false, "Usuario no encontrado", null);
        }
    }

}
