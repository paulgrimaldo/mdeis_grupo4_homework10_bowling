package com.mdeis.group4.bowlinggame.game.scoreprocessor;

import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.shared.GameParameters;

public class ScoreStrategyFactory {
    private ScoreStrategyFactory() {
    }

    public static ScoreStrategy buildFor(Frame frame) {
        if (frame.getPoints().length == 1 && frame.sumOfPoints() == GameParameters.STRIKE) {
            return new StrikeScoreStrategy();
        } else if (frame.getPoints().length == 2 && frame.sumOfPoints() == GameParameters.MAX_PINFALL) {
            return new SpareScoreStrategy();
        } else return new SimpleScoreStrategy();
    }
}
