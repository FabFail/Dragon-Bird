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

        Figure f = g.getPlayerAtTurn();

        f.getStatManager().startPowerNap();

        return true;
    }

    @Override
    public boolean preValidate(GameState g, String[] args) {
        if (!super.validate(args)) {
            return false;
        }

        return g.getPlayerAtTurn().getPhase() == FigurePhase.ATTACK;
    }
}
