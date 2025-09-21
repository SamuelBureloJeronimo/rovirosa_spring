package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoHtml(String to, String subjet, String htmlContenido) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subjet);
        helper.setText(htmlContenido, true); // true => interpreta como HTML
        helper.setFrom("tu_correo@gmail.com");

        mailSender.send(mensaje);
    }

}
