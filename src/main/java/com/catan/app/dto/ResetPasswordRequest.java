package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;

/**
 * This class represents the request body for a reset password request.
 */
public class ResetPasswordRequest {
    @NotNull
    public String username;
}
