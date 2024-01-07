package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

/**
 * This class represents the request body for a register request.
 */
public class RegisterRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;
    @NotNull
    public String email;
}
