package com.example.demo.managers;

import com.example.demo.datastore.DataSink;
import com.example.demo.enums.MatchResult;
import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.exceptions.InvalidTeamException;
import com.example.demo.models.Match;
import com.example.demo.models.Player;
import com.example.demo.models.Scorer;
import com.example.demo.models.Team;

public class MatchManager {

    public void addMatch(Match match) {
        DataSink.matchMap.putIfAbsent(match.getMatchId(), match);
    }

    public void addTeam(Team team) {
        DataSink.teamMap.putIfAbsent(team.getName(), team);
    }

    public void addPlayer(Player player, String team) throws InvalidTeamException {
        if (DataSink.teamMap.get(team) == null)
            throw new InvalidTeamException("Invalid team");
        DataSink.playerMap.putIfAbsent(player.getName(), player);

    }

    public void addScorer(Scorer scorer, String match) throws InvalidMatch {
        if (DataSink.matchMap.get(match) == null)
            throw new InvalidMatch("Invalid match");
        DataSink.matchMap.get(match).getScorers().add(scorer);
        DataSink.scorerMap.putIfAbsent(scorer.getName(), scorer);
    }

    public Player getPlayer(String playerId){
        Player player = DataSink.playerMap.get(playerId);
        return player;
    }

    public void updateWinner(String matchId, String teamName) throws InvalidMatch {
        if (DataSink.matchMap.get(matchId) == null)
            throw new InvalidMatch("Invalid match");
        Match match = DataSink.matchMap.get(matchId);
        match.setMatchResult(MatchResult.FINISHED);
        match.setWinner(teamName);
    }

    public Match showMatchDetails(String matchId) throws InvalidMatch {
        if (DataSink.matchMap.get(matchId) == null)
            throw new InvalidMatch("Invalid match");
        Match match = DataSink.matchMap.get(matchId);
        return match;
    }
}
