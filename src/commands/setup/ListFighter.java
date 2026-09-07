package commands.setup;

import commands.core.Command;
import game.GameState;
import figure.Figure;
import game.GameSetupDatabase;

import java.util.List;

/**
 * Handles validation and execution of the command to list figures.
 * @author ulprv
 */
public class ListFighter extends Command {

    /**
     * creates the command that lists fighters.
     */
    public ListFighter() {
        super("list fighter");
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!validate(args)) {
            return false;
        }

        GameSetupDatabase dbInstance = GameSetupDatabase.getInstance();
        List<Figure> figures = dbInstance.getFigureRepository().getAllFigures();

        if (figures.isEmpty()) {
            IO.println("ERROR: There are no figures yet");
            return false;
        }

        for (Figure f : figures) {
            System.out.println(f);
        }

        return true;
    }
}
