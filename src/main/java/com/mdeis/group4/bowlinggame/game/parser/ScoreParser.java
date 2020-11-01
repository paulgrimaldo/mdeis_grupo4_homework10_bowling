package com.mdeis.group4.bowlinggame.game.parser;

import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;

public interface ScoreParser {
    int parseToNumericScore(String inputScore) throws InvalidInputScoreException;
}
