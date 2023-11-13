package com.catan.app.service;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.LeaderboardEntry;
import com.catan.app.entity.Player;
import com.catan.app.repository.LeaderboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLeaderboardEntry() {
        String username = "testUser";
        Long score = 100L;
        String date = "2023-11-13T12:00:00";

        Player player = new Player();
        player.setPlayerName(username);
        when(playerService.findUserByUsername(username)).thenReturn(player);

        leaderboardService.addLeaderboardEntry(username, score, date);

        verify(playerService).findUserByUsername(username);
        verify(leaderboardRepository).save(any(Leaderboard.class));
    }

    @Test
    void testFindWeeklyScores() {
        Long playerId = 1L;
        Long score = 100L;
        LocalDateTime now = LocalDateTime.now();

        Player player = new Player();
        player.setId(playerId);
        player.setPlayerName("testUser");
        when(playerService.findUserById(playerId)).thenReturn(player);

        // Mock the repository behavior
        when(leaderboardRepository.getDistinctPlayerIds()).thenReturn(Collections.singletonList(playerId));
        when(leaderboardRepository.findScoresWithGivenDate(playerId, now.minusDays(7))).thenReturn(score);

        Long mockPlayerId = leaderboardRepository.getDistinctPlayerIds().get(0);
        Long mockScore = leaderboardRepository.findScoresWithGivenDate(playerId, now.minusDays(7));

        // Call the method under test
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.findWeeklyScores();

        // Assertions to verify the contents of the leaderboardEntries list
        assertThat(leaderboardEntries).isNotNull();
        assertThat(leaderboardEntries).hasSize(1);
        LeaderboardEntry entry = leaderboardEntries.get(0);
        assertThat(entry.getUsername()).isEqualTo(player.getPlayerName());
        assertThat(entry.getScore()).isEqualTo(score);

        // Verify interactions with the mock objects
        verify(leaderboardRepository).getDistinctPlayerIds();
        verify(leaderboardRepository).findScoresWithGivenDate(playerId, now.minusDays(7));
        verify(playerService).findUserById(playerId);
    }

    @Test
    void testFindMonthlyScores() {
        Long playerId = 1L;
        Long score = 100L;
        LocalDateTime now = LocalDateTime.now();

        Player player = new Player();
        player.setId(playerId);
        player.setPlayerName("testUser");
        when(playerService.findUserById(playerId)).thenReturn(player);

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setPlayerId(player);
        leaderboard.setScore(score);

        // Mock the repository behavior
        when(leaderboardRepository.getDistinctPlayerIds()).thenReturn(Collections.singletonList(playerId));
        when(leaderboardRepository.findScoresWithGivenDate(playerId, now.minusDays(30))).thenReturn(score);

        // Call the method under test
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.findMonthlyScores();

        // Assertions to verify the contents of the leaderboardEntries list
        assertThat(leaderboardEntries).isNotNull();
        assertThat(leaderboardEntries).hasSize(1);
        LeaderboardEntry entry = leaderboardEntries.get(0);
        assertThat(entry.getUsername()).isEqualTo(player.getPlayerName());
        assertThat(entry.getScore()).isEqualTo(score);

        // Verify interactions with the mock objects
        verify(leaderboardRepository).getDistinctPlayerIds();
        verify(leaderboardRepository).findScoresWithGivenDate(playerId, now.minusDays(30));
        verify(playerService).findUserById(playerId);
    }

    // Similar tests can be written for findMonthlyScores and findAllTimeScores
}
