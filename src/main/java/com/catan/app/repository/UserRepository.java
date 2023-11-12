package com.catan.app.repository;

import com.catan.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.hashedPassword = ?2, u.salt = ?3 WHERE u.username = ?1")
    void updatePasswordAndSalt(String username, String hash, String salt);
}
