package com.catan.app.repository;

import com.catan.app.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represents the leaderboard repository.
 */
@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    @Query("SELECT DISTINCT l.playerId.id FROM Leaderboard l")
    List<Long> getDistinctPlayerIds();
   @Query("SELECT SUM(l.score) FROM Leaderboard l WHERE l.playerId.id = :playerId AND l.date > :date GROUP BY l.playerId")
    Long findScoresWithGivenDate(@Param("playerId") Long playerId, @Param("date") LocalDateTime date);
}
