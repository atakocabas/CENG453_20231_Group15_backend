package com.catan.app.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents the player entity.
 */
@Getter
@Setter
@Entity
@Table
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PLAYER_ID")
    private Long id;
    @Column(name = "PLAYER_NAME", unique = true)
    private String playerName;
    @Column(name = "HASHED_PASSWORD")
    private String hashedPassword;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "SALT")
    private String salt;
}
