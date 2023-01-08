package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Team {

    private final String name;
    private List<Player> players;

    public Team(String name) {
        this.name = name;
        players = new ArrayList<>();
    }
}
