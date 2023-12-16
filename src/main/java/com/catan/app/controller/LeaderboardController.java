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

@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/leaderboard")
public class LeaderboardController {
    LeaderboardService leaderboardService;

    @Autowired
    LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addLeaderboardEntry(@Validated @RequestBody LeaderboardEntryRequest request) {
        leaderboardService.addLeaderboardEntry(request.username, request.score, request.date);
    }

    @GetMapping("/weekly")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getWeeklyLeaderboard() {
        return leaderboardService.findWeeklyScores();
    }

    @GetMapping("/monthly")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getMonthlyLeaderboard() {
        return leaderboardService.findMonthlyScores();
    }

    @GetMapping("/all-time")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LeaderboardEntry> getAllTimeLeaderboard() {
        return leaderboardService.findAllTimeScores();
    }
}
