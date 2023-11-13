package com.catan.app.repository;

import com.catan.app.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    @Query("SELECT DISTINCT l.playerId.id FROM Leaderboard l")
    List<Long> getDistinctPlayerIds();
    @Query("SELECT SUM(l.score) FROM Leaderboard l WHERE l.date > CURRENT_DATE - 7 AND l.playerId.id IN ?1 GROUP BY l.playerId")
    Long findWeeklyScores(Long playerId);
    @Query("SELECT SUM(l.score) FROM Leaderboard l WHERE l.date > CURRENT_DATE - 30 AND l.playerId.id IN ?1 GROUP BY l.playerId")
    Long findMonthlyScores(Long playerId);
    @Query("SELECT SUM(l.score) FROM Leaderboard l WHERE l.playerId.id IN ?1 GROUP BY l.playerId")
    Long findAllTimeScores(Long playerId);
}
