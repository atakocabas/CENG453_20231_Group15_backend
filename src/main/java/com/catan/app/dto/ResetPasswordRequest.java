package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

public class ResetPasswordRequest {
    @NotNull
    public String username;
}
