package com.mdeis.group4.bowlinggame.game.gameprocessor;

import com.mdeis.group4.bowlinggame.game.calculator.PlayerScoreCalculator;
import com.mdeis.group4.bowlinggame.game.calculator.ScoreCalculator;
import com.mdeis.group4.bowlinggame.model.Player;

import java.util.List;

public class GameProcessorImpl implements GameProcessor {
    private final List<Player> players;
    private final ScoreCalculator scoreCalculator;

    public GameProcessorImpl(List<Player> players) {
        this(players, new PlayerScoreCalculator());
    }

    public GameProcessorImpl(List<Player> players, ScoreCalculator scoreCalculator) {
        this.players = players;
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public List<Player> getPlayerGames() {
        return this.players;
    }

    @Override
    public void processData() {
        for (Player player : this.players) {
            this.scoreCalculator.calculate(player);
        }
    }
}
