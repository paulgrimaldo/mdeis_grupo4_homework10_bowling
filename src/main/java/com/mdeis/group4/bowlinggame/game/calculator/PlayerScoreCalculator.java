package com.mdeis.group4.bowlinggame.game.calculator;

import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.game.organizer.FrameOrganizer;
import com.mdeis.group4.bowlinggame.game.organizer.PlayerFrameOrganizer;
import com.mdeis.group4.bowlinggame.game.scoreprocessor.ScoreStrategyFactory;
import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.model.Player;
import com.mdeis.group4.bowlinggame.shared.GameParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlayerScoreCalculator implements ScoreCalculator {
    public static final Logger log = LogManager.getLogger();
    private static final String invalidNumberOfAttemptsTemplate = "Invalid Number of Attempts [%d]";

    private final FrameOrganizer frameOrganizer = new PlayerFrameOrganizer();

    @Override
    public void calculate(Player player) {
        try {
            this.frameOrganizer.organize(player);
            this.computeFrameScore(player.getFrames());
            player.setValidGame(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            player.setValidGame(Boolean.FALSE);
        }
    }

    private void validateAttempts(List<Frame> frames) throws Exception {
        if (frames.size() != GameParameters.MAX_FRAMES_LENGTH) {
            throw new InvalidInputScoreException(
                    String.format(invalidNumberOfAttemptsTemplate, frames.size()));
        }
    }

    private void computeFrameScore(List<Frame> frames) throws Exception {
        this.validateAttempts(frames);
        for (Frame f : frames) {
            ScoreStrategyFactory.buildFor(f).score(f.getIndex(), frames);
        }
    }
}
