package com.example.demo.service;

import com.example.demo.datastore.DataSink;
import com.example.demo.enums.PlayerResponsibility;
import com.example.demo.enums.PlayerType;
import com.example.demo.enums.TossAction;
import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.exceptions.InvalidTeamException;
import com.example.demo.managers.MatchManager;
import com.example.demo.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchService {

    private MatchManager matchManager;

    public MatchService(MatchManager matchManager){
        this.matchManager=matchManager;
    }

    public Team registerNewTeam(String teamName, Map<String, PlayerType> players) throws InvalidTeamException {
        Team team = new Team(teamName);
        matchManager.addTeam(team);
        team.getPlayers().addAll(getSquad(players));
        for (Player player : team.getPlayers()) {
            matchManager.addPlayer(player, team.getName());
        }

        return team;
    }

    private List<Player> getSquad(Map<String, PlayerType> playersMap) {
        List<Player> players = new ArrayList<>();
        playersMap.forEach((playerName, playerType) -> {
            Player player1 = new Player(playerName);
            player1.setPlayerResponsibility(PlayerResponsibility.PLAYER);
            player1.setPlayerType(playerType);
            players.add(player1);
        });
        return players;
    }

    public void addCaptainAndViceCaptain(Player captain, Player viceCaptain){
        captain.setPlayerResponsibility(PlayerResponsibility.CAPTAIN);
        viceCaptain.setPlayerResponsibility(PlayerResponsibility.VICE_CAPTAIN);
    }

    public Match addNewMatch(Team teamA, Team teamB, String venue){
        Match match = new Match(new TeamsBetween(teamA, teamB));
        match.setMatchId(teamA.getName() + "VS" + teamB.getName());
        match.setVenue(venue);
        matchManager.addMatch(match);
        return match;
    }

    public void addToss(Player playerA, Player playerB, Match match, Team tossWinnerTeam, TossAction tossAction){
        Toss toss = new Toss();
        toss.setTossedBy(playerA.getName());
        toss.setAskedBy(playerB.getName());

        toss.setWonByTeam(tossWinnerTeam.getName());
        toss.setTossAction(tossAction);
        match.setToss(toss);
    }

    public void addScorer(Scorer scorer, String match) throws InvalidMatch {
        matchManager.addScorer(scorer, match);
    }

    public Player getPlayer(String playerId){
        return matchManager.getPlayer(playerId);
    }

    public void updateWinner(String matchId, Team team) throws InvalidMatch {
        matchManager.updateWinner(matchId, team.getName());
    }

    public void showMatchDetails(String matchId) throws InvalidMatch {
        Match match = matchManager.showMatchDetails(matchId);
        System.out.println(match.toString());
    }
}
