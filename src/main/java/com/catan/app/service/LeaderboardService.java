package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.entity.Player;
import com.catan.app.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return generateLeaderboardEntries(getWeeklyScores(leaderboardRepository.getDistinctPlayerIds()));
    }

    public List<LeaderboardEntry> findMonthlyScores() {
        return generateLeaderboardEntries(getMonthlyScores(leaderboardRepository.getDistinctPlayerIds()));
    }

    public List<LeaderboardEntry> findAllTimeScores() {
        return generateLeaderboardEntries(getAllTimeScores(leaderboardRepository.getDistinctPlayerIds()));
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

    private Map<Long, Long> getWeeklyScores(List<Long> playerIdList) {
        Map<Long, Long> playerScores = new HashMap<>();
        for (Long playerId : playerIdList) {
            Long score = leaderboardRepository.findWeeklyScores(playerId);
            playerScores.put(playerId, score);
        }
        return playerScores;
    }

    private Map<Long, Long> getMonthlyScores(List<Long> playerIdList) {
        Map<Long, Long> playerScores = new HashMap<>();
        for (Long playerId : playerIdList) {
            Long score = leaderboardRepository.findMonthlyScores(playerId);
            playerScores.put(playerId, score);
        }
        return playerScores;
    }

    private Map<Long, Long> getAllTimeScores(List<Long> playerIdList) {
        Map<Long, Long> playerScores = new HashMap<>();
        for (Long playerId : playerIdList) {
            Long score = leaderboardRepository.findAllTimeScores(playerId);
            playerScores.put(playerId, score);
        }
        return playerScores;
    }
}
