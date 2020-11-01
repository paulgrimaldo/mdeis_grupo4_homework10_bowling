package com.mdeis.group4.bowlinggame.reader;

import com.mdeis.group4.bowlinggame.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerGameDataImpl implements PlayerGameData {

    public static final Logger log = LogManager.getLogger();

    private final List<Player> players = new ArrayList<>();
    private final Scanner scanner;

    public PlayerGameDataImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Player> loadPlayers() {
        while (this.scanner.hasNext()) {
            this.readLineAndProcess(this.scanner.nextLine());
        }
        return players;
    }

    private void readLineAndProcess(String nextLine) {
        String[] values = nextLine.trim().toUpperCase().split(" ");
        String playerName = values[0];
        String point = values[1];
        Player player =
                this.findPlayerGameByPlayerName(playerName)
                        .orElseGet(
                                () -> {
                                    Player newPlayer = new Player(playerName);
                                    this.players.add(newPlayer);
                                    return newPlayer;
                                });
        player.getScores().add(point);
    }

    private Optional<Player> findPlayerGameByPlayerName(String playerName) {
        return this.players.stream().filter(pg -> playerName.equals(pg.getName())).findFirst();
    }
}
