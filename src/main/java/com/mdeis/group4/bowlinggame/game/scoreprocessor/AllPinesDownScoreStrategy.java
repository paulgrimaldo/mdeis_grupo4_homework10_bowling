package com.mdeis.group4.bowlinggame.game.scoreprocessor;

import com.mdeis.group4.bowlinggame.game.organizer.FrameOrganizer;
import com.mdeis.group4.bowlinggame.game.organizer.PlayerFrameOrganizer;
import com.mdeis.group4.bowlinggame.shared.GameParameters;
import com.mdeis.group4.bowlinggame.model.Frame;

import java.util.List;

class AllPinesDownScoreStrategy implements ScoreStrategy {
    private final FrameOrganizer frameOrganizer = new PlayerFrameOrganizer();
    private final int nextPointSize;

    public AllPinesDownScoreStrategy(int nextPointSize) {
        this.nextPointSize = nextPointSize;
    }

    @Override
    public void score(int frameIndex, List<Frame> frames) {
        int score =
                (frameIndex == 1)
                        ? GameParameters.MIN_PINFALL
                        : this.frameOrganizer.getByIndex(frameIndex - 1, frames).getScore();

        Frame currentFrame = this.frameOrganizer.getByIndex(frameIndex, frames);
        score += currentFrame.sumOfPoints();

        int nextValueCounter = 0;
        int nextFrameDistance = 1;

        while (nextValueCounter < this.nextPointSize) {
            final Frame nextFrame = this.frameOrganizer.getByIndex(frameIndex + nextFrameDistance, frames);
            final int pointSize = nextFrame.getPoints().length;
            nextFrameDistance++;

            if (pointSize == 3) {
                if (nextValueCounter == 0) {
                    score += (nextFrame.getPoints()[0] + nextFrame.getPoints()[1]);
                    nextValueCounter += 2;
                }

                if (nextValueCounter == 1) {
                    score += nextFrame.getPoints()[0];
                    nextValueCounter += 1;
                }

                continue;
            }

            if (nextValueCounter == 0) {
                score += this.nextPointSize == 1 ? nextFrame.getPoints()[0] : nextFrame.sumOfPoints();
                nextValueCounter += pointSize;
                continue;
            }

            if (nextValueCounter == 1) {
                score += nextFrame.getPoints()[0];
                nextValueCounter += pointSize;
            }
        }

        currentFrame.setScore(score);
    }
}
