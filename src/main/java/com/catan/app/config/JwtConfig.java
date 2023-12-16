package com.catan.app.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtConfig {
    private String secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = generateSecretKey(32); // 32 bytes = 256 bits
    }

    public String getSecretKey() {
        return secretKey;
    }

    private String generateSecretKey(int length) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[length];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}