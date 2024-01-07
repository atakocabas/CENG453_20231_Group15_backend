package com.catan.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the leaderboard entry entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaderboardEntry {
    private String username;
    private Long score;
}
