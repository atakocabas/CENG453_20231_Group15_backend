package com.catan.app.repository;

import com.catan.app.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TestEntityManager testEntityManager;
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
        assertThat(savedPlayer.getId()).isNotNull();
    }

    @Test
    void testUpdatePasswordAndSalt() {
        // Save a new user
        Player player = new Player();
        player.setPlayerName("testUser");
        player.setHashedPassword("oldHash");
        player.setSalt("oldSalt");
        playerRepository.save(player);

        // Update the user's password and salt
        playerRepository.updatePasswordAndSalt("testUser", "newHash", "newSalt");

        // Sometimes the persistence context has to manually synced with the database
        // in order to see the updated values
        testEntityManager.flush();
        testEntityManager.clear();

        // Check that the user's password and salt have been updated
        Player updatedPlayer = playerRepository.findUserByPlayerName("testUser");
        assertThat(updatedPlayer).isNotNull();
        assertThat(updatedPlayer.getHashedPassword()).isEqualTo("newHash");
        assertThat(updatedPlayer.getSalt()).isEqualTo("newSalt");
    }
}
