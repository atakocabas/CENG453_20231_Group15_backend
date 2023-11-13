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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LEADERBOARD_ID")
    private Long id;
    @ManyToOne(targetEntity = Player.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAYER_ID")
    private Player playerId;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "SCORE")
    private Long score;

    public void getUser(Player player) {
        this.playerId = player;
    }
    public void setUser(Player player) {
        this.playerId = player;
    }
}