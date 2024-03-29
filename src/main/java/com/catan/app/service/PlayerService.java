package com.catan.app.service;

import com.catan.app.config.JwtConfig;
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
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * This class represents the player service.
 */
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final MailService mailService;
    private final JwtConfig jwtConfig;

    /**
     * This method registers a player.
     * @param username the username
     * @param password the password
     * @param email the email
     * @return the http status
     */
    public HttpStatus register(String username, String password, String email) {
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
        return HttpStatus.CREATED;
    }

    /**
     * This method finds a player by username and password.
     * @param username the username
     * @param password the password
     * @return the player
     */
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

    /**
     * This method finds a player by id.
     * @param id the id
     * @return the player
     */
    public Player findUserById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    /**
     * This method logs in a player.
     * @param username the username
     * @param password the password
     * @return the response entity
     */
    public ResponseEntity<String> login(String username, String password) {
        Player player = findUserByUsernameAndPassword(username, password);

        if (player != null) {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + TimeUnit.HOURS.toMillis(6)); // 6 hour expiration
            String jwtToken = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                    .compact();
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method finds a player by username.
     * @param username the username
     * @return the player
     */
    public Optional<Player> findUserByUsername(String username) {
        return playerRepository.findUserByPlayerName(username);
    }

    /**
     * This method resets a player's password.
     * @param username the username
     * @return the http status
     */
    public HttpStatus resetPassword(String username) {
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

        return HttpStatus.OK;
    }

    /**
     * This method generates a random password.
     * @param length the length
     * @return the random password
     */
    private String generateRandomPassword(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }


    /**
     * This method creates a random salt.
     * @return the salt
     */
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
