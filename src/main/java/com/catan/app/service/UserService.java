package com.catan.app.service;

import com.catan.app.entity.User;
import com.catan.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;

    public Boolean register(String username, String password, String email) {
        if(userRepository.findUserByUsername(username) != null) {
            throw new IllegalStateException("Username already taken!");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        // Generate a Salt
        byte[] salt = getSalt();
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        // Hash the password with salt
        String hashedPassword = hashPassword(password, salt);
        user.setHashedPassword(hashedPassword);
        user.setSalt(saltBase64);

        userRepository.save(user);
        return Boolean.TRUE;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            String saltBase64 = user.getSalt();
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            String hashedPassword = hashPassword(password, salt);
            if (hashedPassword.equals(user.getHashedPassword())) {
                return user;
            }
        }
        return null;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<HttpStatus> login(String username, String password) {
        User user = findUserByUsernameAndPassword(username, password);
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Boolean resetPassword(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null) {
            throw new IllegalStateException("User does not exist!");
        }

        String newPassword = generateRandomPassword(10);

        byte[] newSalt = getSalt();
        String newSaltBase64 = Base64.getEncoder().encodeToString(newSalt);

        String hashedNewPassword = hashPassword(newPassword, newSalt);

        userRepository.updatePasswordAndSalt(username, hashedNewPassword, newSaltBase64);

        mailService.sendMail(user, newPassword);

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
