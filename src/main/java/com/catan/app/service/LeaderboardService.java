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

/**
 * This class represents the leaderboard service.
 */
@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;
    private final PlayerService playerService;

    /**
     * This method adds a leaderboard entry.
     * @param username the username
     * @param score the score
     * @param date the date
     */
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
        Optional<Player> player = playerService.findUserByUsername(username);
        if(player.isEmpty()) {
            throw new IllegalArgumentException("Invalid username");
        }
        leaderboard.setUser(player.get());
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

    /**
     * This method generates leaderboard entries.
     * @param scores the scores
     * @return the leaderboard entries
     */
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

    /**
     * This method gets scores with given date.
     * @param playerIds the player ids
     * @param date the date
     * @return the scores with given date
     */
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
