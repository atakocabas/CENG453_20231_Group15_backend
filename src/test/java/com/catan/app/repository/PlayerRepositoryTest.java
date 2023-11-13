package com.catan.app.repository;

import com.catan.app.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Test
    void testUserSave() {
        // Create a new user
        Player player = new Player();
        player.setPlayerName("testuser");
        player.setHashedPassword("hashedPassword");
        player.setEmail("testuser@example.com");
        player.setSalt("salt");

        // Save the user
        Player savedPlayer = playerRepository.save(player);

        // Check that the user has been saved and has an ID
        Assertions.assertNotNull(savedPlayer.getId());
    }
}
