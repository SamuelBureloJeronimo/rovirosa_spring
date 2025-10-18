package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    LoginQueryDTO findByPasswordAndCorreo(String password, String correo);
    LoginQueryDTO findByPasswordAndPersona_Curp(String password, String curp);

    Boolean existsByPersona_Curp(String curp);
    Boolean existsByCorreo(String correo);

    List<UsuarioQueryDTO> findByRol(String rol);
}
