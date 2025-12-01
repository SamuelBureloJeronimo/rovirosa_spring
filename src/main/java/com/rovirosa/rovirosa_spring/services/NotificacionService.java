package com.rovirosa.rovirosa_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Notificaciones.NotificacionDTO;
import com.rovirosa.rovirosa_spring.models.Notificacion;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.NotificacionRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.INotificacion;

import jakarta.transaction.Transactional;

@Service
public class NotificacionService implements INotificacion {

    @Autowired
    private NotificacionRepository notifRep;
    @Autowired
    private UsuarioRepository userRep;
    @Autowired
    private FirebaseService firebaseService;

    @Override
    public ApiResponse<List<NotificacionDTO>> getNotificaciones(Integer userId) {
        return new ApiResponse<>(true, "Notificaciones obtenidas", notifRep.findByUser_Id(userId));
    }

    @Transactional
    @Override
    public ApiResponse<Void> leerNotificacion(Integer id) {
        int rowsAffected = notifRep.markAsRead(id);
        if (rowsAffected > 0) {
            return new ApiResponse<>(true, "Notificación marcada como leída", null);
        } else {
            return new ApiResponse<>(false, "Notificación no encontrada", null);
        }
    }

    @Override
    public ApiResponse<Void> create(Integer userId, String titulo, String mensaje) {
        Notificacion notif = new Notificacion();
        notif.setTitulo(titulo);
        notif.setSubtitulo(mensaje);
        //Asigar usuario
        Usuario user = userRep.findById(userId).orElse(null);

        if(user == null)
            return new ApiResponse<>(false, "Usuario no encontrado", null);
        
        notif.setUser(user);
        notifRep.save(notif);

        System.out.println("Creando notificación para el usuario ID " + userId + ": " + titulo + " - " + mensaje);
        System.out.println("Token FCM del usuario: " + user.getTokenFmc());
        
        if(user.getTokenFmc() != null && !user.getTokenFmc().isEmpty())
            firebaseService.sendNotification(user.getTokenFmc(), titulo, mensaje);

        return new ApiResponse<>(true, "Notificación creada", null);
    }

}
