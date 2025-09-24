package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.services.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/mail")
public class EmailController {

    /**
     * Service for handling email-related operations.
     * This service is automatically injected by Spring's dependency injection mechanism.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Handles POST requests to send an HTML email.
     *
     * @param to       the recipient's email address
     * @param subjet   the subject of the email (note: parameter name may be a typo, should be 'subject')
     * @param contenido the HTML content of the email
     * @return a message indicating whether the email was sent successfully or an error occurred
     */
    @PostMapping("/send")
    public ResponseEntity<Void> enviarHtml(@RequestParam String to, @RequestParam String subjet, @RequestParam String contenido) {
        try {
            emailService.sendEmailWithHtml(to, subjet, contenido);
            System.out.println("Correo HTML enviado correctamente");
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
