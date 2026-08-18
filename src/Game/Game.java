package Game;

import Commands.Command;
import Commands.CommandRepository;

import java.util.Optional;

public final class Game {
    CommandRepository commands;
    GameState g;
    InputManager in;

    private int seed;

    public Game(int seed) {
        this.seed = seed;
        this.commands = new CommandRepository();
        this.g = new GameState();
        this.in = new InputManager();
    }

    public void run() {
        try {
            setup();
            while(g.isRunning()) {
                startOfTurn();
                boolean turnConsumed = false;
                while(!turnConsumed) {
                    turnConsumed = handlePlayerInput();
                }
                endOfTurn();
            }
        } finally {
            // end of game conditions?
            this.in.close();
        }
    }

    /**
     * Handles all actions before the main game loop is starting
     */
    private void setup() {
        System.out.println("Game setup is done");
        // shuffle of player deck
    }

    public void startOfTurn() {
        // turn init
        System.out.println("Current Turn number: " + g.incrementGameTurn());
    }

    public void endOfTurn() {
        // turn cleanup
    }

    private boolean handlePlayerInput() {
        String input = in.getNextPlayerInput();
        // parse to Command
        Optional<Command> result = commands.find(input);

        if(result.isEmpty()) {
            System.out.println("Command does not exist");
            return  false;
        } else {
            Command command = result.get();
            System.out.println("Executing command: " + command.getKeyword());
            command.execute(g);

            return true;
        }
    }
}
