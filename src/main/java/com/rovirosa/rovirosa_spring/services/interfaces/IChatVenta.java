package com.rovirosa.rovirosa_spring.services.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.ChatVenta.ChatVentaGetDTO;
import com.rovirosa.rovirosa_spring.DTOs.ChatVenta.ChatVentaPostDTO;

public interface IChatVenta {
    
    ApiResponse<List<ChatVentaGetDTO>> getMessagesByVentaId(Integer ventaId);

    ApiResponse<ChatVentaGetDTO> sendMessage(ChatVentaPostDTO dto, MultipartFile archivo);

}
