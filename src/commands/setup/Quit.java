package commands.setup;

import commands.core.Command;
import game.GameState;

/**
 * command to end the game.
 * @author ulprv
 */
public class Quit extends Command {

    /**
     * creates the command that ends the game.
     */
    public Quit() {
        super("quit");
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        g.quitGame();
        return true;
    }
}
