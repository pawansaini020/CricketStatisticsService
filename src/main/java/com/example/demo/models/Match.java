package com.example.demo.models;

import com.example.demo.enums.MatchResult;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Match {
    private String matchId;
    private final TeamsBetween teamsBetween;
    private String venue;
    private Toss toss;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String lost;
    private MatchResult matchResult;
    private Map<Integer, Innings> innings;
    private List<Scorer> scorers;
    private String winner;

    public Match(TeamsBetween teamsBetween) {
        this.teamsBetween = teamsBetween;
        innings = new HashMap<>();
        scorers = new ArrayList<>();
    }
}
