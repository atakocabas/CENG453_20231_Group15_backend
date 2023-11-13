package com.catan.app.repository;

import com.catan.app.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    @Query("SELECT l.playerId, SUM(l.score) FROM Leaderboard l WHERE l.date > CURRENT_DATE - 7 GROUP BY l.playerId")
    Map<Long, Long> findWeeklyScores();
    @Query("SELECT l.playerId, SUM(l.score) FROM Leaderboard l WHERE l.date > CURRENT_DATE - 30 GROUP BY l.playerId")
    Map<Long, Long> findMonthlyScores();
    @Query("SELECT l.playerId, SUM(l.score) FROM Leaderboard l GROUP BY l.playerId")
    Map<Long, Long> findAllTimeScores();

}
