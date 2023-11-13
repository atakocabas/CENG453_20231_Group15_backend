package com.catan.app.repository;

import com.catan.app.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    void testUserSave() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setHashedPassword("hashedPassword");
        user.setEmail("testuser@example.com");
        user.setSalt("salt");

        // Save the user
        User savedUser = userRepository.save(user);

        // Check that the user has been saved and has an ID
        Assertions.assertNotNull(savedUser.getId());
    }
}
