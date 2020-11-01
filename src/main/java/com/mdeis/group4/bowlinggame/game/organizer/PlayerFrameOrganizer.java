package com.mdeis.group4.bowlinggame.game.organizer;

import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.game.parser.PlayerScoreParser;
import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.model.Player;
import com.mdeis.group4.bowlinggame.shared.GameParameters;
import com.mdeis.group4.bowlinggame.game.parser.ScoreParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerFrameOrganizer implements FrameOrganizer {
    private final ScoreParser scoreParser = new PlayerScoreParser();

    @Override
    public List<Frame> organize(Player player)
            throws InvalidInputScoreException {
        player.setFrames(this.buildFrames(player.getScores()));

        return player.getFrames();
    }

    private List<Frame> buildFrames(List<String> inputPoints) throws InvalidInputScoreException {
        Iterator<String> iterator = inputPoints.iterator();
        List<Frame> frames = new ArrayList<>();
        int index = 1;

        while (iterator.hasNext()) {
            String currentInput = iterator.next();
            int currentValue = this.scoreParser.parseToNumericScore(currentInput);

            if (currentValue == GameParameters.STRIKE && index != GameParameters.MAX_FRAMES_LENGTH) {
                String[] points = {currentInput};
                frames.add(new Frame(index, points));
                index++;
                continue;
            }

            String[] points;
            if (index != GameParameters.MAX_FRAMES_LENGTH) {
                points = new String[]{currentInput, iterator.next()};
            } else {
                points = new String[]{currentInput, iterator.next(), iterator.next()};
            }
            frames.add(new Frame(index, points));
            index++;
        }

        return frames;
    }
}
