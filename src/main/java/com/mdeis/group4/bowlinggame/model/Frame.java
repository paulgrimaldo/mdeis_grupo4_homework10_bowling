package com.mdeis.group4.bowlinggame.model;

import com.mdeis.group4.bowlinggame.game.parser.PlayerScoreParser;
import com.mdeis.group4.bowlinggame.game.parser.ScoreParser;
import com.mdeis.group4.bowlinggame.shared.GameParameters;

import java.util.Arrays;

public class Frame {

    private final ScoreParser scoreParser = new PlayerScoreParser();

    private int index;
    private String[] stringPoints;
    private int[] points;
    private int score;

    public Frame(int index, String[] stringPoints) {
        this.index = index;
        this.stringPoints = stringPoints;
        this.score = GameParameters.MIN_PINFALL;
        this.points =
                Arrays.stream(this.stringPoints)
                        .mapToInt(
                                p -> {
                                    try {
                                        return this.scoreParser.parseToNumericScore(p);
                                    } catch (Exception e) {
                                        return GameParameters.ERROR;
                                    }
                                })
                        .filter(p -> p != GameParameters.ERROR)
                        .toArray();
    }

    public int[] getPoints() {
        return this.points;
    }

    public String[] getStringPoints() {
        return this.stringPoints;
    }

    public int getIndex() {
        return this.index;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public int sumOfPoints() {
        return Arrays.stream(this.stringPoints)
                .mapToInt(
                        p -> {
                            try {
                                return this.scoreParser.parseToNumericScore(p);
                            } catch (Exception e) {
                                return GameParameters.ERROR;
                            }
                        })
                .sum();
    }

    public boolean isFirstFrame() {
        return this.index == 1;
    }
}
