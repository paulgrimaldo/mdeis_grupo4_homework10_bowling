package com.mdeis.group4.bowlinggame.game.parser;

import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.shared.GameParameters;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerScoreParser implements ScoreParser {
    private static final Logger log = LogManager.getLogger();

    private final String warningTemplate = "Invalid Score found [%s], this invalidates the PlayerGame";

    @Override
    public int parseToNumericScore(String inputScore)
            throws InvalidInputScoreException {
        if (Objects.isNull(inputScore)) {
            fireInvalidInputException("null");
            return GameParameters.MIN_PINFALL;
        }
        final String sanitizedInputScore = inputScore.trim().toUpperCase();
        if (GameParameters.FOUL_VALUE.equals(sanitizedInputScore)) {
            return GameParameters.MIN_PINFALL;
        }

        try {
            final int score = Integer.parseInt(sanitizedInputScore);
            if (score < GameParameters.MIN_PINFALL || score > GameParameters.MAX_PINFALL) {
                fireInvalidInputException(sanitizedInputScore);
            }
            return score;
        } catch (NumberFormatException exception) {
            fireInvalidInputException(sanitizedInputScore);
        }
        return GameParameters.MIN_PINFALL;
    }

    private void fireInvalidInputException(String inputScore) throws InvalidInputScoreException {
        log.info(String.format(warningTemplate, inputScore));
        throw new InvalidInputScoreException(String.format(warningTemplate, inputScore));
    }
}
