package com.catan.app.repository;

import com.catan.app.entity.Leaderboard;
import com.catan.app.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LeaderboardRepositoryTest {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGetDistinctPlayerIds() {
        // Create and save players
        Player player1 = new Player();
        player1.setPlayerName("player1");
        testEntityManager.persist(player1);

        Player player2 = new Player();
        player2.setPlayerName("player2");
        testEntityManager.persist(player2);

        // Create and save leaderboard entries
        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setPlayerId(player1);
        leaderboard1.setScore(100L);
        leaderboard1.setDate(LocalDateTime.now());
        testEntityManager.persist(leaderboard1);

        Leaderboard leaderboard2 = new Leaderboard();
        leaderboard2.setPlayerId(player2);
        leaderboard2.setScore(150L);
        leaderboard2.setDate(LocalDateTime.now());
        testEntityManager.persist(leaderboard2);

        // Test getDistinctPlayerIds
        List<Long> playerIds = leaderboardRepository.getDistinctPlayerIds();
        assertThat(playerIds).containsExactlyInAnyOrder(player1.getId(), player2.getId());
    }

    @Test
    void testFindScoresWithGivenDate() {
        // Create and save a player
        Player player = new Player();
        player.setPlayerName("testPlayer");
        testEntityManager.persist(player);

        // Create and save leaderboard entries
        LocalDateTime now = LocalDateTime.now();
        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setPlayerId(player);
        leaderboard1.setScore(100L);
        leaderboard1.setDate(now.minusDays(1));
        testEntityManager.persist(leaderboard1);

        Leaderboard leaderboard2 = new Leaderboard();
        leaderboard2.setPlayerId(player);
        leaderboard2.setScore(200L);
        leaderboard2.setDate(now);
        testEntityManager.persist(leaderboard2);

        // Test findScoresWithGivenDate
        Long score = leaderboardRepository.findScoresWithGivenDate(player.getId(), now.minusHours(2));
        assertThat(score).isEqualTo(200L);
    }
}
