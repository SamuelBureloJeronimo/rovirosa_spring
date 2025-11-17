package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class FirebaseService {

    @Autowired
    private UsuarioRepository userRep;

    public String sendNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .putData("title", title)
                    .putData("body", body)
                    .putData("click_action", "FLUTTER_NOTIFICATION_CLICK") // necesario para Android
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notificación enviada con ID: " + response);
            return response;

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "❌ Error enviando notificación: " + e.getMessage();
        }
    }

    @Transactional
    public Boolean saveTokenFMC(Integer usuarioId, String tokenFMC) {

        Usuario usuario = userRep.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return false;
        }
        if (usuario.getTokenFmc() != null && usuario.getTokenFmc().equals(tokenFMC)) {
            return true; // El token ya está guardado
        }
        System.out.println("🔄 Actualizando token FCM para el usuario ID: " + usuarioId);
        System.out.println("🔄 Nuevo token FCM: " + tokenFMC);
        userRep.removeToken(tokenFMC); // Eliminar el token de cualquier otro usuario que lo tenga
        usuario.setTokenFmc(tokenFMC);
        userRep.save(usuario);
        return true;
    }

}
