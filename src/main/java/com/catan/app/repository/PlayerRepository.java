package com.catan.app.repository;

import com.catan.app.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This class represents the player repository.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.playerName = ?1")
    Optional<Player> findUserByPlayerName(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Player p SET p.hashedPassword = ?2, p.salt = ?3 WHERE p.playerName = ?1")
    void updatePasswordAndSalt(String playerName, String hash, String salt);
}
