package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.services.FirebaseService;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {

    @Autowired
    private FirebaseService notificationService;

    @PostMapping("/enviar")
    public ResponseEntity<ApiResponse<String>> enviarNotificacion(@RequestParam String token) {

        System.out.println("🔔 Enviando notificación al token: " + token);
        String title = "Nuevo pedido 🛒";
        String body = "Tienes un nuevo pedido asignado.";
        return ResponseEntity.ok(new ApiResponse<>(true, "Notificación enviada", notificationService.sendNotification(token, title, body)));
    }
    
}
