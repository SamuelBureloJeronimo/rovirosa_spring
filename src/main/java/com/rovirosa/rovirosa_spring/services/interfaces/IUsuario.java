package com.rovirosa.rovirosa_spring.services.interfaces;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Usuarios.MyPerfilDTO;

public interface IUsuario {
    
    ApiResponse<MyPerfilDTO> getPerfilById(Integer id);

    ApiResponse<Void> changeCorreo(Integer id, String nuevoCorreo);

    ApiResponse<Void> changePassword(Integer id, String nuevaPassword, String actualPass);

}
