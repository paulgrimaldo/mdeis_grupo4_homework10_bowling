package com.mdeis.group4.bowlinggame;

import com.mdeis.group4.bowlinggame.game.gameprocessor.GameProcessor;
import com.mdeis.group4.bowlinggame.game.gameprocessor.GameProcessorImpl;
import com.mdeis.group4.bowlinggame.reader.PlayerGameDataImpl;
import com.mdeis.group4.bowlinggame.model.Player;
import com.mdeis.group4.bowlinggame.reader.PlayerGameData;
import com.mdeis.group4.bowlinggame.printer.ScorePrinter;
import com.mdeis.group4.bowlinggame.printer.ScorePrinterImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BowlingGameLauncher {
    public static Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            tryRunBowlingGame(args);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            System.exit(-1);
        } finally {
            System.exit(0);
        }
    }

    private static void tryRunBowlingGame(String[] args) throws FileNotFoundException {
        Scanner scannerFile = new Scanner(new File(args[0]));
        PlayerGameData playerGameData = new PlayerGameDataImpl(scannerFile);
        List<Player> players = playerGameData.loadPlayers();

        GameProcessor dataManager = new GameProcessorImpl(players);
        dataManager.processData();

        ScorePrinter scorePrinter = new ScorePrinterImpl();
        scorePrinter.print(dataManager);
    }
}
