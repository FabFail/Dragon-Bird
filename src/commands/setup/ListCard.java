package commands.setup;

import cards.Card;
import commands.core.Command;
import game.GameState;
import game.GameSetupDatabase;

import java.util.List;

/**
 * Handles validation and execution of the command to list cards created.
 * @author ulprv
 */
public class ListCard extends Command {

    /**
     * creates the command that lists cards.
     */
    public ListCard() {
        super("list card");
    }

    @Override
    protected boolean execute(GameState g, String[] args) {

        boolean result = validate(args);
        if (!result) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }

        List<Card> cards = GameSetupDatabase
                .getInstance()
                .getCardRepository()
                .getListOfCards();

        if (cards.isEmpty()) {
            IO.println("ERROR: No Cards created yet!");
            return false;
        }

        for (Card c : cards) {
            IO.println(c);
        }

        return true;
    }
}
