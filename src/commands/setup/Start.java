package commands.setup;

import commands.core.Argument;
import commands.core.Command;
import game.GameState;
import figure.Figure;
import game.GameSetupDatabase;

/**
 * .
 * @author ulprv
 */
public class Start extends Command {
    /**
     * .
     */
    public Start() {
        super("start");
        schema.add(new Argument("namePlayer", String.class, true));
        schema.add(new Argument("nameAI", String.class, true));
    }

    /**
     * validates and executes the command against the input parameters, then starts the game.
     *
     * @param g is the state if the game
     * @param args are the input parameters of the command
     * @return true if action is consumed
     */
    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!validate(args)) {
            return false;
        }
        String playerName = args[0];
        String aiName = args[1];

        GameSetupDatabase dbInstance = GameSetupDatabase.getInstance();
        Figure playerFigure = dbInstance.getFigureRepository().getFigureByName(playerName);
        Figure aiFigure = dbInstance.getFigureRepository().getFigureByName(aiName);

        if (playerFigure == null || aiFigure == null) {
            IO.println("ERROR: No Figures of these names exist");
            return false;
        }

        if (playerFigure.getCardManager().hasNoDeck() || aiFigure.getCardManager().hasNoDeck()) {
            IO.println("ERROR: Figures have no decks assigned to them");
        }

        g.startGame(playerFigure, aiFigure);
        IO.println(playerName + " and " + aiName + " battle!");
        return true;
    }
}
