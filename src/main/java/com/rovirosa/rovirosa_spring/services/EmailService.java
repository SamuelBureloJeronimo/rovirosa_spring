package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * and is intended to be injected where email sending capabilities are required.</p>
 */
@Service
public class EmailService implements IEmail {

    @Autowired
    private UsuarioRepository userRep;


    
    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException
    {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("samuelbj0608@gmail.com");
        mailSender.setPassword("xafj attx njcu mgqk");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("samuelbj0608@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public Boolean validate(String mail) {

        Boolean exist = userRep.existsByCorreo(mail);        
        if(exist)
            return true;
        else
            return false;
    }

}
