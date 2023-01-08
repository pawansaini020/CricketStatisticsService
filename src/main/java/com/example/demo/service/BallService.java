package com.example.demo.service;

import com.example.demo.enums.BallType;
import com.example.demo.enums.RunType;
import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.models.*;

public class BallService {

    public void addBall(Integer overNumber, Scorer scorer, Match match, Integer inningNumber, String bowledBy, BallType ballType, RunType runType, WicketType wicketType) throws InvalidMatch {
        Ball ball = new Ball(overNumber, bowledBy, scorer.getName());
        if(wicketType!=null){
            Wicket wicket = new Wicket(WicketType.BOWLED);
            ball.setWicket(wicket);
        }
        ball.setRunType(runType);
        ball.setBallType(ballType);
        scorer.setScore(ball, match.getMatchId(), inningNumber);
    }

    public Scorer addScorer(Match match, Player player){
        Scorer scorer = new Scorer(player.getName());
        match.getScorers().add(scorer);
        return scorer;
    }
}
