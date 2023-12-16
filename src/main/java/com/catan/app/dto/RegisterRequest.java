package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;
    @NotNull
    public String email;
}
