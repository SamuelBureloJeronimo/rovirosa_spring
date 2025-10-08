package com.rovirosa.rovirosa_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rovirosa.rovirosa_spring.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByPasswordAndCorreo(String password, String correo);
    Usuario findByPasswordAndPersona_Curp(String password, String curp);
    Usuario findByPasswordAndPersona_Tel(String password, String tel);

    Boolean existsByPersona_Curp(String curp);
    Boolean existsByPersona_Tel(String tel);
    Boolean existsByCorreo(String correo);


}
