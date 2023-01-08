package com.example.demo.models;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PlayerScore {
    private String name;
    private Integer runsScored;
    private Integer ballFaced;
    private WicketType wicketType;
    private String bowler;
    private boolean isOut;
    private int totalBoundaries;
    private int totalSixes;

    public PlayerScore(String name) {
        this.name = name;
        runsScored = 0;
        ballFaced = 0;
    }

}
