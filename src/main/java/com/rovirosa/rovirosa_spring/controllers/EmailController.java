package com.rovirosa.rovirosa_spring.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.clases.CodigoVerificacion;
import com.rovirosa.rovirosa_spring.services.EmailService;

@RestController
@RequestMapping("/api/v1/mail")
public class EmailController {

    private final Map<String, CodigoVerificacion> codigoStorage = new ConcurrentHashMap<>();

    /**
     * Service for handling email-related operations.
     * This service is automatically injected by Spring's dependency injection
     * mechanism.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Handles POST requests to send an HTML email.
     *
     * @param to        the recipient's email address
     * @param subjet    the subject of the email (note: parameter name may be a
     *                  typo, should be 'subject')
     * @param contenido the HTML content of the email
     * @return a message indicating whether the email was sent successfully or an
     *         error occurred
     */
    @PostMapping("/send-code")
    public ResponseEntity<HashMap<String, String>> enviarCodigo(@RequestParam String to) {
        HashMap<String, String> response = new HashMap<>();
        try {
            // 1. Generar código aleatorio de 6 dígitos
            String codigo = String.format("%06d", new Random().nextInt(999999));
            System.out.println(codigo);

            CodigoVerificacion guardado = codigoStorage.get(to);
            if (guardado == null) {
                System.out.println("No existe, generar uno nuevo.");
                // 2. Guardarlo (ejemplo simple en memoria, puedes usar DB o Redis)
                codigoStorage.put(to, new CodigoVerificacion(codigo, LocalDateTime.now().plusMinutes(5)));
            } else {
                codigoStorage.remove(to);
                codigoStorage.put(to, new CodigoVerificacion(codigo, LocalDateTime.now().plusMinutes(5)));
            }

            // 3. Crear contenido HTML
            String htmlContenido = "Tu código de verificación es: " + codigo + " - Válido por 5 minutos.";

            emailService.sendEmailWithHtml(to, "Código de verificación", htmlContenido);
            response.put("Código enviado a:", to);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<HashMap<String, String>> verificarCodigo(@RequestParam String to,
            @RequestParam String codigo) {
        CodigoVerificacion guardado = codigoStorage.get(to);
        HashMap<String, String> response = new HashMap<>();
        if (guardado == null) {
            response.put("error", "No se ha generado un código para este correo.");
            return ResponseEntity.badRequest().body(response);
        }

        if (guardado.getExpira().isBefore(LocalDateTime.now())) {
            response.put("error", "El código ha expirado.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!guardado.getCodigo().equals(codigo)) {
            response.put("error", "El código ha expirado.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", "Código validado correctamente");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/validate/{correo}")
    public ResponseEntity<HashMap<String, Boolean>> validate(@PathVariable String correo) {
        HashMap<String, Boolean> response = new HashMap<>();
        Boolean userExist = emailService.validate(correo);
        response.put("existe", userExist);
        System.out.println(userExist);
        if (userExist) {
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

}