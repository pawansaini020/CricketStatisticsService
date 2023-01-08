package com.example.demo.service;

import com.example.demo.datastore.DataSink;
import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.models.*;

public class LiveStaticService {

    public void showLiveMatchPlayerStatic(String playerId, Match match, Integer inningNumber) throws InvalidMatch {
        ScoreCard scoreCard = ScoreCard.INSTANCE(match.getMatchId(), inningNumber);
        PlayerScore playerScore = scoreCard.getPlayerScores().get(playerId);
        if(playerScore!=null){
            System.out.println(playerScore.toString());
        } else{
            System.out.println("Player " + playerId + " haven't score anything till now.");
        }

    }

    public void showLiveMatchBallerStatic(String playerId, Match match, Integer inningNumber) throws InvalidMatch {
        ScoreCard scoreCard = ScoreCard.INSTANCE(match.getMatchId(), inningNumber);
        BowlerOver bowlerOver = scoreCard.getBowlerOvers().get(playerId);
        if(bowlerOver!=null){
            System.out.println(bowlerOver.toString());
        } else{
            System.out.println("Player " + playerId + " haven't score anything till now.");
        }

    }

    public void showLiveMatchStatic(Match match, Integer inningNumber) throws InvalidMatch {
        System.out.println(ScoreCard.INSTANCE(match.getMatchId(), inningNumber));
    }
}
