package com.example.demo.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Innings {
    private int inningsNumber;
    private Map<Integer, Over> overs;

    public Innings(int inningsNumber) {
        this.inningsNumber = inningsNumber;
        overs = new HashMap<>();
    }
}
