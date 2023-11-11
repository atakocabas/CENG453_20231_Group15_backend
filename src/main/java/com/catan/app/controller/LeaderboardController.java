package com.catan.app.controller;

import com.catan.app.entity.User;
import com.catan.app.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
        public void addLeaderboardEntry(@RequestBody User user, @RequestBody Long score, @RequestBody Date date) {
        leaderboardService.addLeaderboardEntry(user, score, date);
    }

    @GetMapping("/weekly")
    @ResponseStatus(value = HttpStatus.OK)
    public void getWeeklyLeaderboard() {
        // TODO: Get the weekly leaderboard.
    }

    @GetMapping("/monthly")
    @ResponseStatus(value = HttpStatus.OK)
    public void getMonthlyLeaderboard() {
        // TODO: Get the monthly leaderboard.
    }

    @GetMapping("/all-time")
    @ResponseStatus(value = HttpStatus.OK)
    public void getAllTimeLeaderboard() {
        // TODO: Get the all-time leaderboard.
    }
}
