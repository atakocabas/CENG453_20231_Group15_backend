package com.catan.app.service;

import com.catan.app.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail() {
        // Arrange
        Player player = new Player();
        player.setEmail("test@example.com");
        String newPassword = "newPassword123";

        // Act
        mailService.sendMail(player, newPassword);

        // Assert
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setSubject("Password Reset");
        expectedMailMessage.setFrom(null); // Since spring does not inject @Value fields in tests, this is null
        expectedMailMessage.setTo("test@example.com");
        expectedMailMessage.setText("Your new password is: " + newPassword);

        verify(javaMailSender).send(refEq(expectedMailMessage));
    }
}
