package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.ChatVenta.ChatVentaGetDTO;
import com.rovirosa.rovirosa_spring.DTOs.ChatVenta.ChatVentaPostDTO;
import com.rovirosa.rovirosa_spring.models.ChatVenta;
import com.rovirosa.rovirosa_spring.models.Ruta;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.models.Venta;
import com.rovirosa.rovirosa_spring.repositories.ChatVentaRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaDetalleRepository;
import com.rovirosa.rovirosa_spring.repositories.RutaRepository;
import com.rovirosa.rovirosa_spring.repositories.VentaRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IChatVenta;

@Service
public class ChatVentaService implements IChatVenta {

    @Autowired
    private ChatVentaRepository chatVentaRep;
    @Autowired
    private StorageService storageService;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private VentaRepository ventaRep;
    @Autowired
    private RutaDetalleRepository rutaDetalleRep;
    @Autowired
    private RutaRepository rutaRep;

    @Override
    public ApiResponse<List<ChatVentaGetDTO>> getMessagesByVentaId(Integer ventaId) {

        List<ChatVentaGetDTO> messages = chatVentaRep.findByVenta_Id(ventaId);
        return new ApiResponse<>(true, "Messages retrieved successfully", messages);

    }

    @Override
    public ApiResponse<ChatVentaGetDTO> sendMessage(ChatVentaPostDTO dto, MultipartFile archivo) {
        ChatVenta chat = new ChatVenta();
        // Asignar la venta
        Venta venta = ventaRep.findById(dto.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        chat.setVenta(venta);
        // Asignar el usuario
        Usuario user = new Usuario();
        user.setId(dto.getUserId());
        chat.setUser(user);
        // Asignar mensaje y archivo
        if (dto.getMensaje() != null)
            chat.setMensaje(dto.getMensaje());

        String directory = "chat_ventas/venta_" + dto.getVentaId() + "/";
        String filename = storageService.generateFileName();

        if (archivo != null && !archivo.isEmpty())
            chat.setArchivo(directory + filename);

        chatVentaRep.save(chat);

        if (archivo != null && !archivo.isEmpty())
            storageService.store(archivo, directory, filename);

        ChatVentaGetDTO responseDto = chatVentaRep.findFirstById(chat.getId());

        template.convertAndSend("/topic/chat-venta/" + dto.getVentaId(), responseDto);

        Integer userId = venta.getCliente().getUsuario().getId();

        // Evitar enviar notificación al mismo usuario que envió el mensaje
        if (!userId.equals(dto.getUserId())) {
            notificacionService.create(
                    userId,
                    "Nuevo mensaje en la venta #" + dto.getVentaId(),
                    dto.getMensaje() != null ? dto.getMensaje() : "Se ha enviado un archivo.");
        } else {
            Integer rutaId = rutaDetalleRep.findFirstByVenta_Id(dto.getVentaId()).getRuta_Id();
            Ruta ruta = rutaRep.findById(rutaId)
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
                    
            Integer repartidorUserId = ruta.getRepartidor().getUsuario().getId();
            notificacionService.create(
                    repartidorUserId,
                    "Nuevo mensaje en la venta #" + dto.getVentaId(),
                    dto.getMensaje() != null ? dto.getMensaje() : "Se ha enviado un archivo.");
            
        }

        return new ApiResponse<>(true, "Message sent successfully", responseDto);
    }

}
