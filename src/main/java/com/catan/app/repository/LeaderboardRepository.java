package com.catan.app.repository;

import com.catan.app.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    // TODO: Write queries to get weekly, monthly, and all-time scores of all users.
}
