package com.rovirosa.rovirosa_spring.services;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class FirebaseService {
    
    public String sendNotification(String token, String title, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .putData("click_action", "FLUTTER_NOTIFICATION_CLICK") // opcional
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notificación enviada con ID: " + response);
            return response;

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "❌ Error enviando notificación: " + e.getMessage();
        }
    }

}
