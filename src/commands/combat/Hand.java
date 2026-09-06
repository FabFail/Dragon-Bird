package commands.combat;

import commands.core.Command;
import game.GameState;

/**
 * handles validation and execution of the command hand.
 *
 * @author ulprv
 */
public class Hand extends Command {
    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     */
    public Hand() {
        super("hand");
    }

    /**
     * first validates the arguments then executes the command.
     *
     * @param g    is the game state
     * @param args are the parameters of a command
     * @return false as it does never consume an Action
     */
    @Override
    protected boolean execute(GameState g, String[] args) {
        if (args.length > 0) {
            IO.println("ERROR: Hand doesn't need any parameters");
            return false;
        }

        g.getPlayer().getCardManager().printHand();

        return false; // never consume an action
    }
}
