package com.catan.app.controller;

import com.catan.app.dto.LeaderboardEntryRequest;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * This class represents a controller for retrieving and adding leaderboard entries.
 */
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/leaderboard")
public class LeaderboardController {
    LeaderboardService leaderboardService;

    /**
     * @param leaderboardService the leaderboard service
     */
    @Autowired
    LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    /**
     * Adds a leaderboard entry to the database.
     *
     * @param request the request containing the username, score, and date of the entry
     */
    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addLeaderboardEntry(@Validated @RequestBody LeaderboardEntryRequest request) {
        leaderboardService.addLeaderboardEntry(request.username, request.score, request.date);
    }

    /**
     * Retrieves the weekly leaderboard entries from the database.
     *
     * @return a list of leaderboard entries
     */
    @GetMapping("/weekly")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getWeeklyLeaderboard() {
        return leaderboardService.findWeeklyScores();
    }

    /**
     * Retrieves the monthly leaderboard entries from the database.
     *
     * @return a list of leaderboard entries
     */
    @GetMapping("/monthly")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getMonthlyLeaderboard() {
        return leaderboardService.findMonthlyScores();
    }

    /**
     * Retrieves the all-time leaderboard entries from the database.
     *
     * @return a list of leaderboard entries
     */
    @GetMapping("/all-time")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getAllTimeLeaderboard() {
        return leaderboardService.findAllTimeScores();
    }
}
