package commands.combat;

import commands.core.Command;
import figure.FigurePhase;
import game.GameState;

/**
 * handles validation and execution of the command chill.
 *
 * @author ulprv
 */
public class Chill extends Command {
    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     */
    public Chill() {
        super("chill");
    }

    @Override
    protected boolean execute(GameState g, String[] args) {

        return true;
    }

    @Override
    public boolean preValidate(GameState g, String[] args) {
        if (!super.validate(args)) {
            return false;
        }

        return g.getPlayerAtTurn().getPhase() == FigurePhase.DEFENSE;
    }
}
