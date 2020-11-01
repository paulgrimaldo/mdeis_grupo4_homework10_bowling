package com.mdeis.group4.bowlinggame.game.scoreprocessor;

public class SpareScoreStrategy extends AllPinesDownScoreStrategy {
    private static final int spareNextPointSize = 1;

    public SpareScoreStrategy() {
        super(spareNextPointSize);
    }
}
