package com.mdeis.group4.bowlinggame.printer;

import com.mdeis.group4.bowlinggame.game.gameprocessor.GameProcessor;
import com.mdeis.group4.bowlinggame.game.parser.PlayerScoreParser;
import com.mdeis.group4.bowlinggame.game.parser.ScoreParser;
import com.mdeis.group4.bowlinggame.exceptions.InvalidInputScoreException;
import com.mdeis.group4.bowlinggame.shared.GameParameters;
import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScorePrinterImpl implements ScorePrinter {
    private final ScoreParser scoreParser = new PlayerScoreParser();

    @Override
    public void print(GameProcessor scoreDataManager) {
        System.out.println(
                String.join(
                        "\n",
                        this.buildFrameRow(),
                        scoreDataManager.getPlayerGames()
                                .stream()
                                .sorted((pg1, pg2) -> Boolean.compare(pg2.isValidGame(), pg1.isValidGame()))
                                .map(this::playerToPrintable)
                                .collect(Collectors.joining("\n"))));
    }

    private String buildFrameRow() {
        String headerRow = "Frame";
        for (int i = 1; i <= GameParameters.MAX_FRAMES_LENGTH; i++) {
            headerRow = headerRow.concat(String.format("\t\t%d", i));
        }
        return headerRow;
    }

    private String playerToPrintable(Player player) {
        if (!player.isValidGame()) {
            return String.format("[%s does not have a valid game]", player.getName());
        }

        final List<Frame> frames = player.getFrames();
        return String.join(
                "\n", player.getName(), this.buildPinfallsRow(frames), this.scoresRowToPrint(frames));
    }

    private String buildPinfallsRow(List<Frame> frames) {
        String rowName = "Pinfalls";
        String framePoints =
                frames
                        .stream()
                        .map(
                                f -> {
                                    try {
                                        return this.pointsToPrint(f);
                                    } catch (Exception e) {
                                        return "Invalid points detected";
                                    }
                                })
                        .collect(Collectors.joining(""));

        return String.join("\t", rowName, framePoints);
    }

    private String scoresRowToPrint(List<Frame> frames) {
        String rowName = "Score";
        String frameScores =
                frames
                        .stream()
                        .map(f -> String.format("%d", f.getScore()))
                        .collect(Collectors.joining("\t\t"));

        return String.join("\t\t", rowName, frameScores);
    }

    public String pointsToPrint(Frame frame) throws InvalidInputScoreException {
        if (frame.getStringPoints().length == 1
                && this.scoreParser.parseToNumericScore(frame.getStringPoints()[0]) == GameParameters.STRIKE) {
            return String.format(frame.getIndex() == 1 ? "\t%s" : "\t\t%s", GameParameters.STRIKE_VALUE);
        }

        if (frame.getStringPoints().length == 2 && frame.sumOfPoints() == GameParameters.MAX_PINFALL) {
            return String.format(frame.getIndex() == 1 ? "%s\t%s" : "\t%s\t%s", frame.getStringPoints()[0], GameParameters.SPARE_VALUE);
        }

        List<String> maskValues = new ArrayList<>();

        for (String stringPoint : frame.getStringPoints()) {
            maskValues.add(this.scoreParser.parseToNumericScore(stringPoint) == GameParameters.MAX_PINFALL ? GameParameters.STRIKE_VALUE : stringPoint);
        }

        String pointsAsString = maskValues.stream().collect(Collectors.joining("\t"));
        return frame.isFirstFrame() ? pointsAsString : "\t".concat(pointsAsString);
    }
}
