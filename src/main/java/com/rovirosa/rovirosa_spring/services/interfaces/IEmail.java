package com.rovirosa.rovirosa_spring.services.interfaces;

import jakarta.mail.MessagingException;

/**
 * Service interface for sending emails.
 * Provides a method to send emails with HTML content to a specified recipient.
 *
 * Implementations of this interface are responsible for handling the email sending logic,
 * including formatting the message and managing any exceptions related to the email process.
 *
 * @author Samuel Burelos Jeronimo
 */
public interface IEmail {
    
    /**
     * Sends an email with HTML content to the specified recipient.
     *
     * @param to the recipient's email address
     * @param subjet the subject of the email
     * @param htmlContenido the HTML content to be sent in the email body
     * @throws MessagingException if an error occurs while sending the email
     */
    public void sendEmailWithHtml(String to, String subjet, String htmlContenido) throws MessagingException;

    /**
     * Validates the given email address.
     *
     * @param mail the email address to validate
     * @throws IllegalArgumentException if the email address is invalid
     */
    public Boolean validate(String mail);
}
