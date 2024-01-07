package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

/**
 * This class represents the request body for a login request.
 */
public class LoginRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;
}