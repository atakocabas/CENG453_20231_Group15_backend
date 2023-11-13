package com.catan.app.service;

import com.catan.app.entity.Player;
import com.catan.app.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTest() {
        String username = "testUser";
        String password = "testPassword";
        String email = "testEmail@test.com";

        when(playerRepository.findUserByPlayerName(username)).thenReturn(null);

        Boolean result = playerService.register(username, password, email);

        assertThat(result).isTrue();
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testFindUserByUsernameAndPassword() {
        // Mock data
        String username = "testUser";
        String password = "testPassword";
        String saltBase64 = "mockedSaltBase64";
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        String hashedPassword = hashPassword(password, salt);

        // Mock repository behavior
        Player mockPlayer = new Player();
        mockPlayer.setPlayerName(username);
        mockPlayer.setSalt(saltBase64);
        mockPlayer.setHashedPassword(hashedPassword);

        when(playerRepository.findUserByPlayerName(username)).thenReturn(mockPlayer);

        // Test the service method
        Player result = playerService.findUserByUsernameAndPassword(username, password);

        // Verify that the repository method was called with the correct argument
        verify(playerRepository).findUserByPlayerName(username);

        // Verify the result
        assertThat(result).isNotNull();
        assertThat(result.getPlayerName()).isEqualTo(username);
        assertThat(result.getHashedPassword()).isEqualTo(hashedPassword);
        assertThat(result.getSalt()).isEqualTo(saltBase64);

    }

    @Test
    void testFindUserById() {
        Long userId = 1L;
        Player mockPlayer = new Player();
        mockPlayer.setId(userId);
        mockPlayer.setPlayerName("testUser");

        when(playerRepository.findById(userId)).thenReturn(Optional.of(mockPlayer));

        Player result = playerService.findUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        verify(playerRepository).findById(userId);

        // Test for not found scenario
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

        result = playerService.findUserById(2L);

        assertThat(result).isNull();
    }

    @Test
    void testFindUserByUsername() {
        String username = "testUser";
        Player mockPlayer = new Player();
        mockPlayer.setPlayerName(username);

        when(playerRepository.findUserByPlayerName(username)).thenReturn(mockPlayer);

        Player result = playerService.findUserByUsername(username);

        assertThat(result).isNotNull();
        assertThat(result.getPlayerName()).isEqualTo(username);
        verify(playerRepository).findUserByPlayerName(username);

        // Test for not found scenario
        when(playerRepository.findUserByPlayerName(anyString())).thenReturn(null);

        result = playerService.findUserByUsername("nonExistingUser");

        assertThat(result).isNull();
    }



    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error during password hashing", e);
        }
    }
}