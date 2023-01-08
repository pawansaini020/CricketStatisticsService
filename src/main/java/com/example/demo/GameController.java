package com.example.demo;

import com.example.demo.enums.BallType;
import com.example.demo.enums.PlayerType;
import com.example.demo.enums.RunType;
import com.example.demo.enums.TossAction;
import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.exceptions.InvalidTeamException;
import com.example.demo.models.*;
import com.example.demo.service.BallService;
import com.example.demo.service.LiveStaticService;
import com.example.demo.service.MatchService;

import java.util.HashMap;
import java.util.Map;

public class GameController {

    private MatchService matchService;

    private BallService ballService;

    private LiveStaticService liveStaticService;

    public GameController(MatchService matchService, BallService ballService, LiveStaticService liveStaticService){
        this.matchService=matchService;
        this.ballService=ballService;
        this.liveStaticService=liveStaticService;
    }

    public void intiGame() throws InvalidMatch, InvalidTeamException {

        Team indianTeam = createIndianTeam();

        // add indian captian and vice captian
        Player indianCaptian = matchService.getPlayer("MS Dhoni");
        Player indianViceCaptian = matchService.getPlayer("VIRAT KHOLI");
        matchService.addCaptainAndViceCaptain(indianCaptian, indianViceCaptian);

        Team sriLankaTeam = createSriLankaTeam();

        // add sriLanka captian and vice captian
        Player sriLankaCaptian = matchService.getPlayer("Dasun Shanaka");
        Player sriLankaviceCaptian = matchService.getPlayer("Kusal Mendis");

        matchService.addCaptainAndViceCaptain(sriLankaCaptian, sriLankaviceCaptian);

        // Add new match
        String venue = "Bangalore Chinnaswamy stadium";
        Match match = matchService.addNewMatch(indianTeam, sriLankaTeam, venue);


        // add toss
        matchService.addToss(indianCaptian, sriLankaCaptian, match, indianTeam, TossAction.BATTING);

        // added indian openers
        Player indian1StBatter = matchService.getPlayer("ROHIT SHARMA");
        Player indian2StBatter = matchService.getPlayer("SHIKAR DHAWAN");

        Scorer scorer1 = ballService.addScorer(match, indian1StBatter);
        Scorer scorer2 = ballService.addScorer(match, indian2StBatter);
        matchService.addScorer(scorer1, match.getMatchId());
        matchService.addScorer(scorer2, match.getMatchId());

        // added baller
        Integer inningNumber = 1;
        Integer overNumber = 1;
        Player sriLanka1stBaller = matchService.getPlayer("Kasun Rajitha");

        // Added first over ball
        ballService.addBall(overNumber, scorer1, match, inningNumber, sriLanka1stBaller.getName(), BallType.NORMAL, RunType.TWO, null);
        ballService.addBall(overNumber, scorer1, match, inningNumber, sriLanka1stBaller.getName(), BallType.NORMAL, RunType.ONE, null);
        ballService.addBall(overNumber, scorer2, match, inningNumber, sriLanka1stBaller.getName(), BallType.NORMAL, RunType.FOUR, null);
        ballService.addBall(overNumber, scorer2, match, inningNumber, sriLanka1stBaller.getName(), BallType.WICKET, RunType.ZERO, WicketType.BOWLED);

        liveStaticService.showLiveMatchStatic(match, inningNumber);

        liveStaticService.showLiveMatchPlayerStatic(scorer1.getName(), match, inningNumber);
        liveStaticService.showLiveMatchPlayerStatic(scorer2.getName(), match, inningNumber);

        Player indian3StBatter = matchService.getPlayer("VIRAT KHOLI");

        Scorer scorer3 = ballService.addScorer(match, indian3StBatter);
        matchService.addScorer(scorer3, match.getMatchId());
        ballService.addBall(overNumber, scorer3, match, inningNumber, sriLanka1stBaller.getName(), BallType.NORMAL, RunType.SIX, null);

        // show live data
        liveStaticService.showLiveMatchPlayerStatic(scorer3.getName(), match, inningNumber);
        ballService.addBall(overNumber, scorer3, match, inningNumber, sriLanka1stBaller.getName(), BallType.NO_BALL, RunType.ONE_NO_BALL, null);

        ballService.addBall(overNumber, scorer1, match, inningNumber, sriLanka1stBaller.getName(), BallType.WIDE, RunType.ZERO, null);

        liveStaticService.showLiveMatchBallerStatic(sriLanka1stBaller.getName(), match, inningNumber);

        ballService.addBall(overNumber, scorer1, match, inningNumber, sriLanka1stBaller.getName(), BallType.NO_BALL, RunType.ONE_LEG_BYE, null);

        liveStaticService.showLiveMatchBallerStatic(sriLanka1stBaller.getName(), match, inningNumber);
        liveStaticService.showLiveMatchPlayerStatic(scorer1.getName(), match, inningNumber);
        liveStaticService.showLiveMatchBallerStatic(sriLanka1stBaller.getName(), match, inningNumber);

        liveStaticService.showLiveMatchStatic(match, inningNumber);


        // Added second inning
        inningNumber = 2;
        overNumber = 1;
        Player indian1stBaller = matchService.getPlayer("BUMRAH");

        // added sriLanka1stBatter
        Player sriLanka1stBatter = matchService.getPlayer("Kusal Mendis");
        Player sriLanka2ndBatter = matchService.getPlayer("Pathum Nissanka");

        Scorer sriLankaScorer1 = ballService.addScorer(match, sriLanka1stBatter);
        Scorer sriLankaScorer2 = ballService.addScorer(match, sriLanka2ndBatter);
        matchService.addScorer(sriLankaScorer1, match.getMatchId());
        matchService.addScorer(sriLankaScorer2, match.getMatchId());

        // added ball
        ballService.addBall(overNumber, sriLankaScorer1, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.ZERO, null);
        ballService.addBall(overNumber, sriLankaScorer1, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.ONE, null);
        ballService.addBall(overNumber, sriLankaScorer2, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.FOUR, null);
        ballService.addBall(overNumber, sriLankaScorer2, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.ONE, null);
        ballService.addBall(overNumber, sriLankaScorer1, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.ZERO, null);
        ballService.addBall(overNumber, sriLankaScorer1, match, inningNumber, indian1stBaller.getName(), BallType.NORMAL, RunType.ONE, null);

        // update match winner
        liveStaticService.showLiveMatchStatic(match, inningNumber);
        liveStaticService.showLiveMatchPlayerStatic(sriLankaScorer1.getName(), match, inningNumber);
        liveStaticService.showLiveMatchBallerStatic(indian1stBaller.getName(), match, inningNumber);

        matchService.updateWinner(match.getMatchId(), indianTeam);

        // show final match details
        matchService.showMatchDetails(match.getMatchId());
    }

    private Team createIndianTeam() throws InvalidMatch, InvalidTeamException {
        //      India team
        String indianTeamName = "India";
        Map<String, PlayerType> indianPlayers = new HashMap<>();
        indianPlayers.put("MS Dhoni", PlayerType.WICKET_KEEPER);
        indianPlayers.put("VIRAT KHOLI", PlayerType.BATSMAN);
        indianPlayers.put("ROHIT SHARMA", PlayerType.BATSMAN);
        indianPlayers.put("SHIKAR DHAWAN", PlayerType.BATSMAN);
        indianPlayers.put("AMBATI RAYUDU", PlayerType.BATSMAN);
        indianPlayers.put("HARDIK PANDYA", PlayerType.ALL_ROUNDER);
        indianPlayers.put("DINESH KARTHIK", PlayerType.BATSMAN);
        indianPlayers.put("BUMRAH", PlayerType.BOWLER);
        indianPlayers.put("SHAMI", PlayerType.BOWLER);
        indianPlayers.put("CHAHAL", PlayerType.BOWLER);
        indianPlayers.put("KULDEEP", PlayerType.BOWLER);

        Team indianTeam = matchService.registerNewTeam(indianTeamName, indianPlayers);
        return indianTeam;
    }

    private Team createSriLankaTeam() throws InvalidMatch, InvalidTeamException {
        // SriLanka team
        String sriLankaTeamName = "SriLanka";
        Map<String, PlayerType> sriLankaPlayers = new HashMap<>();
        sriLankaPlayers.put("Pathum Nissanka", PlayerType.WICKET_KEEPER);
        sriLankaPlayers.put("Kusal Mendis", PlayerType.BATSMAN);
        sriLankaPlayers.put("Avishka Fernando", PlayerType.BATSMAN);
        sriLankaPlayers.put("Dhananjaya de Silva", PlayerType.BATSMAN);
        sriLankaPlayers.put("Charith Asalanka", PlayerType.BATSMAN);
        sriLankaPlayers.put("Dasun Shanaka", PlayerType.ALL_ROUNDER);
        sriLankaPlayers.put("Wanindu Hasaranga", PlayerType.BATSMAN);
        sriLankaPlayers.put("Chamika Karunaratne", PlayerType.BOWLER);
        sriLankaPlayers.put("Maheesh Theekshana", PlayerType.BOWLER);
        sriLankaPlayers.put("Kasun Rajitha", PlayerType.BOWLER);
        sriLankaPlayers.put(" Dilshan Madushanka", PlayerType.BOWLER);
        Team sriLankaTeam = matchService.registerNewTeam(sriLankaTeamName, sriLankaPlayers);
        return sriLankaTeam;
    }
}
