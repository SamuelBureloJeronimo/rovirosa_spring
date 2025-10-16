package com.rovirosa.rovirosa_spring.services;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.DTOs.EmpresaConfig.GmailPassDTO;
import com.rovirosa.rovirosa_spring.repositories.EmpresaConfigRepository;
import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IEmail;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Service implementation for sending emails using JavaMailSender.
 * Provides functionality to send HTML formatted emails.
 *
 * This service is annotated with {@link org.springframework.stereotype.Service}
 * and is intended to be injected where email sending capabilities are required.
 * </p>
 */
@Service
public class EmailService implements IEmail {

    @Autowired
    private UsuarioRepository userRep;
    @Autowired
    private EmpresaConfigRepository empRep;

    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        System.out.println("Enviando correo a: " + to);

        GmailPassDTO config = empRep.findFirstBy();
        if (config == null) {
            throw new RuntimeException("Configuración de correo no encontrada");
        }

        System.out.println("Config: " + config.getEmailApp() + " / " + config.getCodigoApp());

        // 🔹 Configurar el mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(config.getEmailApp());
        mailSender.setPassword(config.getCodigoApp());

        // ⚙️ Propiedades SMTP obligatorias
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true"); // para ver los logs en consola

        // 📩 Crear el mensaje
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getEmailApp());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // 🚀 Enviar
        mailSender.send(message);

        System.out.println("Correo enviado exitosamente a " + to);
    }

    @Override
    public Boolean validate(String mail) {

        Boolean exist = userRep.existsByCorreo(mail);
        if (exist)
            return true;
        else
            return false;
    }

}
