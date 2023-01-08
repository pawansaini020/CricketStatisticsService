package com.example.demo.models;

import com.example.demo.enums.PlayerResponsibility;
import com.example.demo.enums.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Player {

    private String name;
    private PlayerResponsibility playerResponsibility;
    private PlayerType playerType;

    public Player(String name) {
        this.name=name;
    }
}
