package com.mdeis.group4.bowlinggame.game.scoreprocessor;

import com.mdeis.group4.bowlinggame.game.organizer.FrameOrganizer;
import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.game.organizer.PlayerFrameOrganizer;
import com.mdeis.group4.bowlinggame.shared.GameParameters;
import com.mdeis.group4.bowlinggame.model.Frame;

import java.util.List;

public class SimpleScoreStrategy implements ScoreStrategy {
    public static final String exceedsMaxScoreTemplate =
            "The current sum of points [%d] in Frame [%s] exceeds the max allowed (%s)";
    private final FrameOrganizer frameOrganizer = new PlayerFrameOrganizer();

    @Override
    public void score(int frameIndex, List<Frame> frames) throws InvalidInputScoreException {
        int score =
                (frameIndex == 1)
                        ? GameParameters.MIN_PINFALL
                        : this.frameOrganizer.getByIndex(frameIndex - 1, frames).getScore();

        Frame currentFrame = this.frameOrganizer.getByIndex(frameIndex, frames);
        int currentSumOfPoints = currentFrame.sumOfPoints();

        if (frameIndex != GameParameters.MAX_FRAMES_LENGTH
                && currentSumOfPoints > GameParameters.MAX_PINFALL) {
            throw new InvalidInputScoreException(
                    String.format(
                            exceedsMaxScoreTemplate, currentSumOfPoints, frameIndex, GameParameters.MAX_PINFALL));
        }

        score += currentFrame.sumOfPoints();
        currentFrame.setScore(score);
    }
}
