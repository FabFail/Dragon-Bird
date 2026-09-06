package commands.combat;

import commands.core.Command;
import figure.Figure;
import figure.FigurePhase;
import game.GameState;

/**
 * handles validation and execution of a power-nap.
 *
 * @author ulpv
 */
public class PowerNap extends Command {

    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     */
    public PowerNap() {
        super("power-nap");
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!super.validate(args)) {
            return false;
        }
        Figure f = g.getPlayerAtTurn();

        if (f.getPhase() == FigurePhase.DEFENSE) {
            return false;
        }

        f.getStatManager().startPowerNap();
        IO.println("OK.");


        return true;
    }
}
