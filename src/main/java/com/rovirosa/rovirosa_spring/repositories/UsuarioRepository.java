package com.rovirosa.rovirosa_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.GerentePv.GerentePvQueryDTO;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.UsuarioQueryDTO;
import com.rovirosa.rovirosa_spring.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    LoginQueryDTO findByPasswordAndCorreo(String password, String correo);
    LoginQueryDTO findByPasswordAndPersona_Curp(String password, String curp);

    Boolean existsByPersona_Curp(String curp);
    Boolean existsByCorreo(String correo);

    List<UsuarioQueryDTO> findByRol(String rol);

    GerentePvQueryDTO findFirstByRolAndPuntoVenta_Id(String rol, Integer id);

    @Modifying
    @Query("UPDATE Usuario p SET p.estado = :estatus WHERE p.id = :id")
    int changeStatus(@Param("id") Integer id, @Param("estatus") String estatus);
}
