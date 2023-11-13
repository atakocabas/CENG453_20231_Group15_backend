package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.entity.Player;
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
    private final PlayerService playerService;

    public void addLeaderboardEntry(String username, Long score, Date date) {
        Leaderboard leaderboard = new Leaderboard();
        Player player = playerService.findUserByUsername(username);
        leaderboard.setUser(player);
        leaderboard.setScore(score);
        leaderboard.setDate(date);
        leaderboardRepository.save(leaderboard);
    }

    public List<LeaderboardEntry> findWeeklyScores() {
        Map<Long, Long> weeklyScores = leaderboardRepository.findWeeklyScores();
        return generateLeaderboardEntries(weeklyScores);
    }

    public List<LeaderboardEntry> findMonthlyScores() {
        Map<Long, Long> monthlyScores = leaderboardRepository.findMonthlyScores();
        return generateLeaderboardEntries(monthlyScores);
    }

    public List<LeaderboardEntry> findAlltimeScores() {
        Map<Long, Long> alltimeScores = leaderboardRepository.findAllTimeScores();
        return generateLeaderboardEntries(alltimeScores);
    }

    private List<LeaderboardEntry> generateLeaderboardEntries(Map<Long, Long> scores){
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        for(Map.Entry<Long, Long> entry : scores.entrySet()) {
            Player player = playerService.findUserById(entry.getKey());
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setUsername(player.getPlayerName());
            leaderboardEntry.setScore(entry.getValue());
            leaderboardEntries.add(leaderboardEntry);
        }
        return leaderboardEntries;
    }

}
