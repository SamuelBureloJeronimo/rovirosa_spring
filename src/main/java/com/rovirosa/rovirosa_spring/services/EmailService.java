package com.rovirosa.rovirosa_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rovirosa.rovirosa_spring.repositories.UsuarioRepository;
import com.rovirosa.rovirosa_spring.services.interfaces.IEmail;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Service implementation for sending emails using JavaMailSender.
 * Provides functionality to send HTML formatted emails.
 *
 * This service is annotated with {@link org.springframework.stereotype.Service}
 * and is intended to be injected where email sending capabilities are required.</p>
 */
@Service
public class EmailService implements IEmail {

    /**
     * The {@code mailSender} is an instance of {@link JavaMailSender} that is automatically
     * injected by Spring's dependency injection mechanism. It is used to send emails
     * from within the service.
     */
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UsuarioRepository userRep;

    /**
     * Sends an email with HTML content to the specified recipient.
     *
     * @param to            the recipient's email address
     * @param subjet        the subject of the email
     * @param htmlContenido the HTML content to be sent in the email body
     * @throws MessagingException if there is a failure in the email sending process
     */
    @Override
    public void sendEmailWithHtml(String to, String subject, String text) throws MessagingException
    {
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
