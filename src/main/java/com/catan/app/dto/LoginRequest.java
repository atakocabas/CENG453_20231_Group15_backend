package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;
}