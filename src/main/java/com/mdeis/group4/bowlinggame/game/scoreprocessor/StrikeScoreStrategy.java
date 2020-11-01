package com.mdeis.group4.bowlinggame.game.scoreprocessor;

public class StrikeScoreStrategy extends AllPinesDownScoreStrategy {
    private static final int strikeNextPointSize = 2;

    public StrikeScoreStrategy() {
        super(strikeNextPointSize);
    }
}
