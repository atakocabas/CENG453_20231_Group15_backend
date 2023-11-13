package com.catan.app.controller;

import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        public void addLeaderboardEntry(
                @RequestParam("username") String username,
                @RequestParam("score") Long score,
                @RequestParam(value = "date", required = false) String date) {
        leaderboardService.addLeaderboardEntry(username, score, date);
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
