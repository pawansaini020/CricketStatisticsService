package com.example.demo.models;

import com.example.demo.datastore.DataSink;
import com.example.demo.enums.BallType;
import com.example.demo.enums.RunType;
import com.example.demo.exceptions.InvalidMatch;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ScoreCard {
    private Map<String, PlayerScore> playerScores = new HashMap<>();
    private Map<String, BowlerOver> bowlerOvers = new HashMap<>();
    private int totalExtras;
    private int totalScore;
    private int totalWickets;
    private int totalWide;
    private int totalNoBall;
    private int totalFours;
    private int totalSixes;
    private String match;
    private Double runRate;
    private int leftBall;
    private int innings;

    private ScoreCard(String match, int innings) throws InvalidMatch {
        if (DataSink.matchMap.get(match) == null)
            throw new InvalidMatch("No match exists");
        this.match = match;
        this.innings = innings;

    }

    public static ScoreCard INSTANCE(String matchName, int inningsNumber) throws InvalidMatch {
        if (DataSink.matchMap.get(matchName) == null)
            throw new InvalidMatch("No match exists");
        Match match = DataSink.matchMap.get(matchName);
        if (DataSink.scoreCardMap.get(matchName) == null || match.getInnings().get(inningsNumber)==null) {
            Innings innings = match.getInnings().get(inningsNumber);
            if (innings == null) {
                innings = new Innings(inningsNumber);
                match.getInnings().put(inningsNumber, innings);
            }
            ScoreCard scoreCard = new ScoreCard(matchName, inningsNumber);
            Map<Integer, ScoreCard> scoreCardMap = DataSink.scoreCardMap.get(matchName);
            if(scoreCardMap==null){
                scoreCardMap = new HashMap<Integer, ScoreCard>();
            }
            scoreCardMap.putIfAbsent(inningsNumber, scoreCard);
            DataSink.scoreCardMap.putIfAbsent(matchName, scoreCardMap);
        }
        return DataSink.scoreCardMap.get(matchName).get(inningsNumber);
    }


    public void setScoreCardForBall(Ball ball) {
        Match match = DataSink.matchMap.get(this.match);
        Innings innings = match.getInnings().get(this.innings);
        addOver(ball.getOverNumber());
        innings.getOvers().get(ball.getOverNumber());
        Over over;
        int run = 0;
        switch (ball.getBallType()) {
            case WIDE:
                run = updateExtras(ball);
                this.totalWide +=1;
                BowlerOver bowlerOver1 = setBowlerOverForBall(ball);
                over = bowlerOver1.getOverMap().get(ball.getOverNumber());
                over.setRunsScored(over.getRunsScored() + run);
                break;
            case NORMAL: {
                getOrCreatePlayerScore(ball);
                run = updateScore(ball);
                BowlerOver bowlerOver = setBowlerOverForBall(ball);
                over = bowlerOver.getOverMap().get(ball.getOverNumber());
                over.getBalls().add(ball);
                over.setRunsScored(over.getRunsScored() + run);
                break;
            }
            case WICKET: {
                PlayerScore playerScore = getOrCreatePlayerScore(ball);
                run = updateScore(ball);
                playerScore.setOut(true);
                totalWickets += 1;
                playerScore.setWicketType(ball.getWicket().getWicketType());
                playerScore.setBowler(ball.getBowledBy());
                BowlerOver bowlerOver = setBowlerOverForBall(ball);
                incrementWicketForBall(ball.getBowledBy(), ball.getBallType(),
                        ball.getWicket().getWicketType());
                over = bowlerOver.getOverMap().get(ball.getOverNumber());
                over.getBalls().add(ball);
                over.setRunsScored(over.getRunsScored() + run);
                break;
            }
            case NO_BALL: {
                run = updateExtras(ball);
                this.totalNoBall +=1;
                BowlerOver bowlerOver2 = setBowlerOverForBall(ball);
                over = bowlerOver2.getOverMap().get(ball.getOverNumber());
                over.setRunsScored(over.getRunsScored() + run);
                break;
            }
        }

    }

    private void addOver(int overNumber) {
        Match match = DataSink.matchMap.get(this.match);
        Innings innings = match.getInnings().get(this.innings);
        innings.getOvers().putIfAbsent(overNumber, new Over(overNumber));
    }

    private PlayerScore getOrCreatePlayerScore(Ball ball) {
        PlayerScore playerScore = this.playerScores.get(ball.getFacedBy());
        if (playerScore == null) {
            playerScore = new PlayerScore(ball.getFacedBy());
            playerScores.put(ball.getFacedBy(), playerScore);
        }
        return playerScore;
    }

    private BowlerOver setBowlerOverForBall(Ball ball) {
        BowlerOver bowlerOver = this.bowlerOvers.get(ball.getBowledBy());
        if (bowlerOver == null) {
            bowlerOver = new BowlerOver(ball.getBowledBy());
            bowlerOvers.put(ball.getBowledBy(), bowlerOver);
        }
        bowlerOver.getOverMap().putIfAbsent(ball.getOverNumber(), new Over(ball.getOverNumber()));
        return bowlerOver;
    }

    private int updateScore(Ball ball) {
        int run = 0;
        switch (ball.getRunType()) {
            case ONE:
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 1);
                incrementScore(1);
                run=1;
                break;
            case TWO:
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 2);
                incrementScore(2);
                run=2;
                break;
            case THREE:
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 3);
                incrementScore(3);
                run=3;
                break;
            case FOUR:
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 4);
                incrementScore(4);
                incrementTotalFour(1);
                run=4;
                break;
            case SIX:
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 6);
                incrementScore(6);
                incrementTotalSix(1);
                run=6;
                break;
        }
        return run;
    }

    private int updateExtras(Ball ball) {
        int run = 0;
        switch (ball.getRunType()) {
            case ONE_BYE:
            case ONE_LEG_BYE:
                incrementScore(1);
                incrementExtras(1);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                run=1;
                break;
            case TWO_BYE:
            case TWO_LEG_BYES:
                incrementScore(2);
                incrementExtras(2);
                incrementExtrasInOver(ball.getOverNumber(), 2);
                run=2;
                break;
            case THREE_BYE:
            case THREE_LEG_BYES:
                incrementScore(3);
                incrementExtras(3);
                incrementExtrasInOver(ball.getOverNumber(), 3);
                run=3;
                break;
            case FOUR_BYE:
            case FOUR_LEG_BYES:
                incrementScore(4);
                incrementExtras(4);
                incrementExtrasInOver(ball.getOverNumber(), 4);
                incrementTotalFour(1);
                run=4;
                break;
            case ONE_WIDE:
            case ZERO_NO_BALL:
                incrementScore(1);
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementExtras(1);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                run=1;
                break;
            case TWO_WIDES:
                incrementScore(2);
                incrementExtras(2);
                incrementExtrasInOver(ball.getOverNumber(), 2);
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                run=2;
                break;
            case THREE_WIDES:
                incrementScore(3);
                incrementExtras(3);
                incrementExtrasInOver(ball.getOverNumber(), 3);
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                run=3;
                break;
            case FOUR_WIDES:
                incrementScore(4);
                incrementExtras(4);
                incrementExtrasInOver(ball.getOverNumber(), 4);
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementTotalFour(1);
                run=4;
                break;
            case FIVE_WIDES:
                incrementScore(5);
                incrementExtras(5);
                incrementExtrasInOver(ball.getOverNumber(), 5);
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                run=5;
                break;
            case ONE_NO_BALL:
                incrementScore(2);
                incrementExtras(1); // one hit + 1 nb
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 1);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                incrementRunsScoredInOver(ball.getOverNumber(), 1);
                run=1;
                break;
            case TWO_NO_BALLS:
                incrementScore(3);
                incrementExtras(1); // one hit + 1 nb
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 2);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                incrementRunsScoredInOver(ball.getOverNumber(), 2);
                run=2;
                break;
            case THREE_NO_BALLS:
                incrementScore(4);
                incrementExtras(1); // one hit + 1 nb
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 3);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                incrementRunsScoredInOver(ball.getOverNumber(), 3);
                run=3;
                break;
            case FOUR_NO_BALLS:
                incrementScore(5);
                incrementExtras(1); // one hit + 1 nb
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 4);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                incrementRunsScoredInOver(ball.getOverNumber(), 4);
                incrementTotalFour(1);
                run=4;
                break;
            case SIX_NO_BALLS:
                incrementScore(7);
                incrementExtras(1); // one hit + 1 nb
                incrementBowlerExtras(ball.getBowledBy(),
                        ball.getBallType(), 1);
                incrementPlayerScore(ball.getFacedBy(), ball.getRunType(), 6);
                incrementExtrasInOver(ball.getOverNumber(), 1);
                incrementRunsScoredInOver(ball.getOverNumber(), 6);
                incrementTotalSix(1);
                run=6;
                break;
        }
        return run;
    }

    private void incrementBowlerExtras(String bowledBy, BallType ballType, int extras) {
        if (bowlerOvers.get(bowledBy) == null) {
            bowlerOvers.putIfAbsent(bowledBy, new BowlerOver(bowledBy));
        }
        if (bowlerOvers.get(bowledBy) != null && bowlerOvers.get(bowledBy).
                getExtrasBowled().get(ballType)
                != null) {

            int extra = bowlerOvers.get(bowledBy).getExtrasBowled().get(ballType);
            bowlerOvers.get(bowledBy).getExtrasBowled()
                    .put(ballType, extra + extras);
        } else

            bowlerOvers.get(bowledBy).getExtrasBowled()
                    .putIfAbsent(ballType, extras);
    }

    private void incrementWicketForBall(String bowledBy, BallType ballType,
                                        WicketType wicketType) {
        if (bowlerOvers.get(bowledBy) == null) {
            bowlerOvers.putIfAbsent(bowledBy, new BowlerOver(bowledBy));
        }
        switch (wicketType) {
            case LBW:
            case CAUGHT:
            case BOWLED:
            case HIT_WICKET:
            case STUMPTED:
                bowlerOvers.get(bowledBy).setWicketsTaken(bowlerOvers.get(bowledBy)
                        .getWicketsTaken() + 1);
        }
    }

    private void incrementPlayerScore(String player, RunType runType, int score) {
        PlayerScore playerScore = playerScores.get(player);
        playerScore.setRunsScored(playerScore.getRunsScored()+score);
        switch (runType) {
            case ZERO:
            case ONE:
            case ONE_BYE:
            case ONE_LEG_BYE:
            case TWO:
            case TWO_BYE:
            case TWO_LEG_BYES:
            case THREE:
            case THREE_BYE:
            case THREE_LEG_BYES:
            case FOUR_BYE:
            case FOUR_LEG_BYES:
                playerScore.setBallFaced(playerScore.getBallFaced()+1);
                break;
            case FOUR:
                playerScore.setBallFaced(playerScore.getBallFaced()+1);
            case FOUR_NO_BALLS:
                playerScore.setTotalBoundaries(playerScore.getTotalBoundaries() + 1);
                break;
            case SIX:
                playerScore.setBallFaced(playerScore.getBallFaced()+1);
            case SIX_NO_BALLS:
                playerScore.setTotalSixes(playerScore.getTotalSixes() + 1);
                break;
        }
    }

    private void incrementScore(int incrementBy) {
        totalScore += incrementBy;
        runRate = (double) totalScore/ (double) bowlerOvers.size();
    }

    private void incrementExtras(int incrementBy) {
        totalExtras += incrementBy;
    }

    private void incrementTotalFour(int incrementBy) {
        totalFours += incrementBy;
    }

    private void incrementTotalSix(int incrementBy) {
        totalSixes += incrementBy;
    }

    private void incrementRunsScoredInOver(int overNumber, int runs) {
        Match match = DataSink.matchMap.get(this.match);
        Innings innings = match.getInnings().get(this.innings);
        Over over = innings.getOvers().get(overNumber);
        over.setRunsScored(over.getRunsScored() + runs);
        over.setTotalRunsInOver(over.getRunsScored() + over.getExtras());
    }

    private void incrementExtrasInOver(int overNumber, int extras) {
        Match match = DataSink.matchMap.get(this.match);
        Innings innings = match.getInnings().get(this.innings);
        Over over = innings.getOvers().get(overNumber);
        over.setExtras(over.getExtras() + extras);
        over.setTotalRunsInOver(over.getRunsScored() + over.getExtras());
    }
}

