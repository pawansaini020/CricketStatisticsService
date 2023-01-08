package com.example.demo.models;

import lombok.Data;

@Data
public class TeamsBetween {
    Team team1;
    Team team2;

    public TeamsBetween(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
}
