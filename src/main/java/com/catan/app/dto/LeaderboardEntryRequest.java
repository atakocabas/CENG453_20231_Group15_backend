package com.catan.app.dto;

import jakarta.validation.constraints.NotNull;
/**
 * This class represents the request body for a leaderboard entry.

 */
public class LeaderboardEntryRequest {
    @NotNull
    public String username;
    @NotNull
    public Long score;
    public String date;

}