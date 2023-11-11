package com.catan.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table
public class User {
    // TODO: Sequence Generator for creating tables.
    @Id
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
}
