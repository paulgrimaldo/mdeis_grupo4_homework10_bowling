package com.mdeis.group4.bowlinggame.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {
    private final String name;
    private final List<String> scores;
    private List<Frame> frames;
    private boolean validGame;

    public Player(String name) {
        this(name, new ArrayList<>());
    }

    public Player(String name, List<String> scores) {
        this.name = name;
        this.scores = scores;
        this.frames = new ArrayList<>();
    }
}
