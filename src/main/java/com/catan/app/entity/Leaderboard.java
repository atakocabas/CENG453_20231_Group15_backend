package com.catan.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class Leaderboard {
    // TODO: Sequence Generator to create tables.
    @Id
    @Column(name = "LEADERBOARD_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "SCORE")
    private Long score;

    public void getUser(User user) {
        this.userId = user;
    }
    public void setUser(User user) {
        this.userId = user;
    }
}