package commands.setup;

import cards.Card;
import commands.core.Argument;
import commands.core.Command;
import game.GameState;
import figure.Figure;
import game.GameSetupDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles validation and execution of the command to set a figures deck.
 * @author ulprv
 */
public class SetDeck extends Command {
    /**
     * creates the command to set a figures deck.
     */
    public SetDeck() {
        super("set deck");
        schema.add(new Argument("name", String.class, true));
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!validate(args)) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }

        String name = args[0];
        GameSetupDatabase db = GameSetupDatabase.getInstance();

        Figure selected = db
                .getFigureRepository()
                .getFigureByName(name);
        // get correct player

        if (selected == null) {
            IO.println("ERROR: No Figure of that Name exists");
            return false;
        } else {
            List<Card> cards = new ArrayList<>();
            String[] indexes = Arrays.copyOfRange(args, 1, args.length);
            for (String idx : indexes) {
                Card c = db
                        .getCardRepository()
                        .getCardByIndex(Integer.parseInt(idx));

                if (c == null) {
                    IO.println("ERROR: Card of Index " + idx + " doesn't exist");
                    return false;
                }
                cards.add(c);
            }
            selected.getCardManager().setDeck(cards);
        }

        return true;
    }

    @Override
    public boolean validate(String[] args) {

        // this might not be necessary and just checks for size...
        if (!super.validate(Arrays.copyOfRange(args, 0, 1))) {
            IO.println("DEBUG: SET DECK " + Arrays.copyOfRange(args, 0, 1).length);
            return false;
        }

        String[] cardIdx = Arrays.copyOfRange(args, 1, args.length);
        for (String idx : cardIdx) {
            if (!isInteger(idx)) {
                return false;
            }
        }

        // create deck
        return true;
    }
}
