package commands.setup;

import commands.core.Argument;
import commands.core.Command;
import game.GameState;
import figure.FigureData;
import game.GameSetupDatabase;

/**
 * Handles validation and execution of the command to create figures.
 * @author ulprv
 */
public class CreateFighter extends Command {
    /**
     * creates the command that creates figures.
     */
    public CreateFighter() {
        super("create fighter");
        schema.add(new Argument("name", String.class, true));
        schema.add(new Argument("health", Integer.class, true));
        schema.add(new Argument("energy", Integer.class, true));
        schema.add(new Argument("power", Integer.class, true));
        schema.add(new Argument("defense", Integer.class, true));
        schema.add(new Argument("speed", Integer.class, true));
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        boolean result = validate(args);
        if (!result) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }

        String name = args[0];
        int health = Integer.parseInt(args[1]);
        int energy = Integer.parseInt(args[2]);
        int power = Integer.parseInt(args[3]);
        int defense = Integer.parseInt(args[4]);
        int speed = Integer.parseInt(args[5]);

        if (health < 0 || energy < 0 || power < 0 || defense < 0 || speed < 0) {
            IO.println("ERROR: Input stats must be non-negative");
            return false;
        }

        FigureData fd = new FigureData(health, energy, power, defense, speed);

        GameSetupDatabase
                .getInstance()
                .getFigureRepository()
                .addFigure(name, fd);

        return true;
    }


}
