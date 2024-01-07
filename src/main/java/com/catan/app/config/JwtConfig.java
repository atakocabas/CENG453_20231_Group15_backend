package com.catan.app.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Configuration class for JWT tokens
 */
@Component
public class JwtConfig {
    private String secretKey;

    /**
     * Generate a random secret key on startup
     */
    @PostConstruct
    public void init() {
        this.secretKey = generateSecretKey(32); // 32 bytes = 256 bits
    }

    /**
     * @return the secret key used to sign JWT tokens
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Generate a random secret key
     *
     * @param length the length of the key in bytes
     * @return a random secret key encoded as a Base64 string
     */
    private String generateSecretKey(int length) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[length];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}