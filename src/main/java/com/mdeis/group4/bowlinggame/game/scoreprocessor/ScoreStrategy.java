package com.mdeis.group4.bowlinggame.game.scoreprocessor;

import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.model.Frame;

import java.util.List;

public interface ScoreStrategy {

    void score(int frameIndex, List<Frame> frames) throws InvalidInputScoreException;
}
