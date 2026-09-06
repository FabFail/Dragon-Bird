package commands.setup;

import commands.core.Argument;
import commands.core.Command;
import game.GameState;
import figure.Figure;
import game.GameSetupDatabase;

/**
 * Handles validation and execution of the command to list deck of figures.
 * @author ulprv
 */
public class ListDeck extends Command {
    /**
     * creates the command to list decks of figures.
     */
    public ListDeck() {
        super("list deck");
        schema.add(new Argument("name", String.class, true));
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!validate(args)) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }

        String name = args[0];
        Figure selected = GameSetupDatabase
                .getInstance()
                .getFigureRepository()
                .getFigureByName(name);

        if (selected == null) {
            IO.println("ERROR: No Figure of that Name exists");
            return false;
        }

        selected.getCardManager().printDeck();
        return true;
    }
}
