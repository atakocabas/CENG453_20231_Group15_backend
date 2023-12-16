package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;
public class LeaderboardEntryRequest {
    @NotNull
    public String username;
    @NotNull
    public Long score;
    public String date;

}