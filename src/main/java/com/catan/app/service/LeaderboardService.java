package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.entity.User;
import com.catan.app.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final UserService userService;

    public void addLeaderboardEntry(User user, Long score, Date date) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setUser(user);
        leaderboard.setScore(score);
        leaderboard.setDate(date);
        leaderboardRepository.save(leaderboard);
    }

    public List<LeaderboardEntry> findWeeklyScores() {
        List<LeaderboardEntry> weeklyScoresList = new ArrayList<>();
        Map<Long, Long> weeklyScores = leaderboardRepository.findWeeklyScores();

        for(Map.Entry<Long, Long> entry : weeklyScores.entrySet()) {
            User user = userService.findUserById(entry.getKey());
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setUsername(user.getUsername());
            leaderboardEntry.setScore(entry.getValue());
            weeklyScoresList.add(leaderboardEntry);
        }

        return weeklyScoresList;
    }

    public List<LeaderboardEntry> findMonthlyScores() {
        List<LeaderboardEntry> monthlyScoresList = new ArrayList<>();
        Map<Long, Long> monthlyScores = leaderboardRepository.findMonthlyScores();

        for(Map.Entry<Long, Long> entry : monthlyScores.entrySet()) {
            User user = userService.findUserById(entry.getKey());
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setUsername(user.getUsername());
            leaderboardEntry.setScore(entry.getValue());
            monthlyScoresList.add(leaderboardEntry);
        }

        return monthlyScoresList;
    }

    public List<LeaderboardEntry> findAlltimeScores() {
        List<LeaderboardEntry> alltimeScoresList = new ArrayList<>();
        Map<Long, Long> alltimeScores = leaderboardRepository.findAllTimeScores();

        for(Map.Entry<Long, Long> entry : alltimeScores.entrySet()) {
            User user = userService.findUserById(entry.getKey());
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setUsername(user.getUsername());
            leaderboardEntry.setScore(entry.getValue());
            alltimeScoresList.add(leaderboardEntry);
        }

        return alltimeScoresList;
    }

}
