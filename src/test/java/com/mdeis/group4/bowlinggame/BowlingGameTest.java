package com.mdeis.group4.bowlinggame;


import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.game.calculator.PlayerScoreCalculator;
import com.mdeis.group4.bowlinggame.game.calculator.ScoreCalculator;
import com.mdeis.group4.bowlinggame.game.organizer.FrameOrganizer;
import com.mdeis.group4.bowlinggame.game.organizer.PlayerFrameOrganizer;
import com.mdeis.group4.bowlinggame.game.parser.PlayerScoreParser;
import com.mdeis.group4.bowlinggame.game.parser.ScoreParser;
import com.mdeis.group4.bowlinggame.game.scoreprocessor.ScoreStrategy;
import com.mdeis.group4.bowlinggame.game.scoreprocessor.SimpleScoreStrategy;
import com.mdeis.group4.bowlinggame.game.scoreprocessor.SpareScoreStrategy;
import com.mdeis.group4.bowlinggame.game.scoreprocessor.StrikeScoreStrategy;
import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BowlingGameTest {
    private ScoreParser scoreParser;
    private FrameOrganizer frameOrganizer;
    private ScoreCalculator scoreCalculator;

    @Before
    public void config() {
        this.scoreParser = new PlayerScoreParser();
        this.frameOrganizer = new PlayerFrameOrganizer();
        this.scoreCalculator = new PlayerScoreCalculator();
    }

    @Test
    public void testInputFaultScore()
            throws InvalidInputScoreException {
        final String score = "F";

        assertEquals(0, this.scoreParser.parseToNumericScore(score));
    }

    @Test
    public void testInputStrikeScore() throws InvalidInputScoreException {
        String score = "10";
        assertEquals(10, this.scoreParser.parseToNumericScore(score));
    }


    @Test
    public void testNoPinsKnockedDown() throws InvalidInputScoreException {
        String score = "0";
        assertEquals(0, this.scoreParser.parseToNumericScore(score));
    }

    @Test(expected = InvalidInputScoreException.class)
    public void testNegativeScore() throws InvalidInputScoreException {
        String score = "-1";
        this.scoreParser.parseToNumericScore(score);
    }


    @Test(expected = InvalidInputScoreException.class)
    public void testExceededScoreAllowed() throws InvalidInputScoreException {
        String score = "11";
        this.scoreParser.parseToNumericScore(score);
    }

    @Test(expected = InvalidInputScoreException.class)
    public void testInputNoFormatScore() throws InvalidInputScoreException {
        String score = "A";
        this.scoreParser.parseToNumericScore(score);
    }


    @Test
    public void testNumberFramePerfectScore()
            throws Exception {
        List<String> points =
                Arrays.asList("10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10");
        Player playerGame = new Player("Test", points);

        final List<Frame> frames = this.frameOrganizer.organize(playerGame);

        assertEquals(10, frames.size());
    }

    @Test
    public void testNumberFrameNoPerfectScore()
            throws Exception {
        List<String> points =
                Arrays.asList("8", "2", "10", "10", "5", "5", "10", "10", "6", "4", "10", "10", "10", "7", "2");
        Player playerGame = new Player("Test", points);

        final List<Frame> frames = this.frameOrganizer.organize(playerGame);

        assertEquals(10, frames.size());
    }

    @Test
    public void testScoreNineFrame()
            throws Exception {
        int indexNineFrame = 9;
        Frame eightFrame = new Frame(8, new String[]{"4", "6"});
        eightFrame.setScore(30);

        List<Frame> frames = Arrays.asList(eightFrame,
                new Frame(indexNineFrame, new String[]{"F", "5"}));

        ScoreStrategy scoreStrategy = new SimpleScoreStrategy();
        scoreStrategy.score(indexNineFrame, frames);
        assertEquals(35, this.frameOrganizer.getByIndex(indexNineFrame, frames).getScore());
    }


    @Test
    public void testScoreJhonEightFrame()
            throws Exception {
        int indexEightFrame = 8;
        Frame frameSeven = new Frame(7, new String[]{"9", "0"});
        frameSeven.setScore(110);

        List<Frame> frames = Arrays.asList(frameSeven,
                new Frame(indexEightFrame, new String[]{"7", "3"}),
                new Frame(9, new String[]{"4", "4"}));

        ScoreStrategy scoreStrategy = new SpareScoreStrategy();
        scoreStrategy.score(indexEightFrame, frames);
        assertEquals(124, this.frameOrganizer.getByIndex(indexEightFrame, frames).getScore());
    }


    @Test
    public void testScoreJeffSecondFrame()
            throws Exception {
        int indexSecondFrame = 2;
        Frame previousFrame = new Frame(1, new String[]{"10"});
        previousFrame.setScore(20);

        List<Frame> frames = Arrays.asList(previousFrame,
                new Frame(indexSecondFrame, new String[]{"7", "3"}),
                new Frame(3, new String[]{"9", "0"}));

        ScoreStrategy scoreStrategy = new SpareScoreStrategy();
        scoreStrategy.score(indexSecondFrame, frames);
        assertEquals(39, this.frameOrganizer.getByIndex(indexSecondFrame, frames).getScore());
    }


    @Test
    public void testExcessPointsInGame() {
        List<String> points =
                Arrays.asList("8", "2", "8", "0", "8", "2", "F", "6",
                        "10", "8", "2", "2", "2", "5", "5", "8", "1", "2", "6", "F", "6", "2",
                        "8", "10", "10", "8", "2", "10");

        final Player playerGame = new Player("Person", points);
        this.scoreCalculator.calculate(playerGame);
        assertFalse(playerGame.isValidGame());
    }

    @Test
    public void testStrikeScoreJeffNinethFrame()
            throws InvalidInputScoreException {
        int indexNinethFrame = 9;
        Frame eightFrame = new Frame(8, new String[]{"10"});
        eightFrame.setScore(120);

        List<Frame> frames = Arrays.asList(eightFrame,
                new Frame(indexNinethFrame, new String[]{"10"}),
                new Frame(10, new String[]{"10", "8", "1"}));

        ScoreStrategy strikeScoreStrategy = new StrikeScoreStrategy();
        strikeScoreStrategy.score(indexNinethFrame, frames);
        assertEquals(148, this.frameOrganizer.getByIndex(indexNinethFrame, frames).getScore());
    }
}
