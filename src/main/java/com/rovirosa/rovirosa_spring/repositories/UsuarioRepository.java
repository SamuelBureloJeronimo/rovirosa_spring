package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginQueryDTO;
import com.rovirosa.rovirosa_spring.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    LoginQueryDTO findByPasswordAndCorreo(String password, String correo);
    LoginQueryDTO findByPasswordAndPersona_Curp(String password, String curp);
    LoginQueryDTO findByPasswordAndPersona_Tel(String password, String tel);

    Boolean existsByPersona_Curp(String curp);
    Boolean existsByPersona_Tel(String tel);
    Boolean existsByCorreo(String correo);


}
