package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.services.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String enviarHtml(
        @RequestParam String to,
        @RequestParam String subjet,
        @RequestParam String contenido) {
        try {
            emailService.enviarCorreoHtml(to, subjet, contenido);
            return "Correo HTML enviado correctamente";
        } catch (MessagingException e) {
            return "Error al enviar: " + e.getMessage();
        }
    }
    
}
