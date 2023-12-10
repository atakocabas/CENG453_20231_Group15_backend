package com.catan.app.service;

import com.catan.app.entity.Player;
import com.catan.app.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final MailService mailService;

    public Boolean register(String username, String password, String email) {
        if(playerRepository.findUserByPlayerName(username).isPresent()) {
            throw new IllegalStateException("Username already taken!");
        }
        Player player = new Player();
        player.setPlayerName(username);
        player.setEmail(email);

        // Generate a Salt
        byte[] salt = getSalt();
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        // Hash the password with salt
        String hashedPassword = hashPassword(password, salt);
        player.setHashedPassword(hashedPassword);
        player.setSalt(saltBase64);

        playerRepository.save(player);
        return Boolean.TRUE;
    }

    public Player findUserByUsernameAndPassword(String username, String password) {
        Optional<Player> player = playerRepository.findUserByPlayerName(username);
        if (player.isPresent()) {
            String saltBase64 = player.get().getSalt();
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            String hashedPassword = hashPassword(password, salt);
            if (hashedPassword.equals(player.get().getHashedPassword())) {
                return player.get();
            }
        }
        return null;
    }

    public Player findUserById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public ResponseEntity<HttpStatus> login(String username, String password) {
        Player player = findUserByUsernameAndPassword(username, password);
        if (player != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Player> findUserByUsername(String username) {
        return playerRepository.findUserByPlayerName(username);
    }

    public Boolean resetPassword(String username) {
        Optional<Player> player = playerRepository.findUserByPlayerName(username);
        if(player.isEmpty()) {
            throw new IllegalStateException("User does not exist!");
        }

        String newPassword = generateRandomPassword(10);

        byte[] newSalt = getSalt();
        String newSaltBase64 = Base64.getEncoder().encodeToString(newSalt);

        String hashedNewPassword = hashPassword(newPassword, newSalt);

        playerRepository.updatePasswordAndSalt(username, hashedNewPassword, newSaltBase64);

        mailService.sendMail(player.get(), newPassword);

        return true;
    }
    private String generateRandomPassword(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }


    private byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
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
