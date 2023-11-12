package com.catan.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "HASHED_PASSWORD")
    private String hashedPassword;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "SALT")
    private String salt;
}
