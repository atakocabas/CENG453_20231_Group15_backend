package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.entity.Player;
import com.catan.app.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final PlayerService playerService;

    public void addLeaderboardEntry(String username, Long score, String date) {
        LocalDateTime requestedDate = LocalDateTime.now();
        if(StringUtils.isNotEmpty(date)) {
            try {
                requestedDate = LocalDateTime.parse(date);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format");
            }
        }
        Leaderboard leaderboard = new Leaderboard();
        Player player = playerService.findUserByUsername(username);
        leaderboard.setUser(player);
        leaderboard.setScore(score);
        leaderboard.setDate(requestedDate);
        leaderboardRepository.save(leaderboard);
    }

    public List<LeaderboardEntry> findWeeklyScores() {
        return generateLeaderboardEntries(getScoresWithGivenDate(leaderboardRepository.getDistinctPlayerIds(), LocalDateTime.now().minusDays(7)));
    }

    public List<LeaderboardEntry> findMonthlyScores() {
        return generateLeaderboardEntries(getScoresWithGivenDate(leaderboardRepository.getDistinctPlayerIds(), LocalDateTime.now().minusDays(30)));
    }

    public List<LeaderboardEntry> findAllTimeScores() {
        return generateLeaderboardEntries(getScoresWithGivenDate(leaderboardRepository.getDistinctPlayerIds(), LocalDateTime.now().minusYears(100)));
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

    private Map<Long, Long> getScoresWithGivenDate(List<Long> playerIds, LocalDateTime date) {
        Map<Long, Long> scores = new HashMap<>();
        for(Long playerId : playerIds) {
            Long score = leaderboardRepository.findScoresWithGivenDate(playerId, date);
            if(score != null) {
                scores.put(playerId, score);
            }
        }
        return scores;
    }
}
