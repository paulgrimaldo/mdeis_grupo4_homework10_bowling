package com.mdeis.group4.bowlinggame.game.organizer;

import com.mdeis.group4.bowlinggame.model.Frame;
import com.mdeis.group4.bowlinggame.model.Player;

import java.util.List;

public interface FrameOrganizer {

    List<Frame> organize(Player player) throws Exception;

    default Frame getByIndex(int index, List<Frame> frames) {
        return frames
                .stream()
                .filter(f -> f.getIndex() == index)
                .findFirst().get();
    }
}
