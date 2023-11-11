package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.User;
import com.catan.app.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;

    @Autowired
    LeaderboardService(LeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }

    public void addLeaderboardEntry(User user, Long score, Date date) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setUser(user);
        leaderboard.setScore(score);
        leaderboard.setDate(date);
        leaderboardRepository.save(leaderboard);
    }

}
