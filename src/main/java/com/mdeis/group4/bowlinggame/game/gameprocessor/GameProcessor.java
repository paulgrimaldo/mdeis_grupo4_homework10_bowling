package com.mdeis.group4.bowlinggame.game.gameprocessor;

import com.mdeis.group4.bowlinggame.model.Player;

import java.util.List;

public interface GameProcessor {

    List<Player> getPlayerGames();

    void processData();
}
