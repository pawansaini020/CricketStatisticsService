package com.example.demo.datastore;

import com.example.demo.models.*;

import java.util.HashMap;
import java.util.Map;

public class DataSink {
    public static Map<String, Match> matchMap = new HashMap<>();
    public static Map<String, Team> teamMap = new HashMap<>();
    public static Map<String, Player> playerMap = new HashMap<>();
    public static Map<String, Scorer> scorerMap = new HashMap<>();
    public static Map<String, Map<Integer, ScoreCard>> scoreCardMap = new HashMap<>();
}
