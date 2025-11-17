package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseEntity.ok(new ApiResponse<>(true, "Notificación enviada",
                notificationService.sendNotification(token, title, body)));
    }

    @PutMapping("/save-token-fmc/{usuarioId}")
    public ResponseEntity<ApiResponse<Boolean>> saveTokenFMC(
            @PathVariable Integer usuarioId,
            @RequestBody String tokenFMC) {
        Boolean res = notificationService.saveTokenFMC(usuarioId, tokenFMC);
        if (res) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Token FCM guardado exitosamente", res));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, "Error al guardar el token FCM", res));
        }
    }

}
