package com.catan.app.service;

import com.catan.app.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This class represents the mail service.
 */
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * This method sends a mail to the player.
     * @param player the player
     * @param newPassword the new password
     */
    void sendMail(Player player, String newPassword) {
        String toEmail = player.getEmail();

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Password Reset");
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(toEmail);
            mailMessage.setText("Your new password is: " + newPassword);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send email");
        }
    }
}
