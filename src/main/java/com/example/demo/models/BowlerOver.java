package com.example.demo.models;

import com.example.demo.enums.BallType;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class BowlerOver {
    private String bowlerName;
    private Map<Integer, Over> overMap;
    private Map<BallType, Integer> extrasBowled;
    private int wicketsTaken;

    public BowlerOver(String bowlerName) {
        this.bowlerName = bowlerName;
        overMap = new HashMap<>();
        extrasBowled = new HashMap<>();
    }
}
