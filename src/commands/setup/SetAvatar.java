package commands.setup;

import commands.core.Argument;
import commands.core.Command;
import figure.Figure;
import game.GameState;
import game.GameSetupDatabase;

/**
 * Handles validation and execution of the command that sets a figures avatar.
 * @author ulprv
 */
public class SetAvatar extends Command {

    /**
     * creates the command that sets a figures avatar.
     */
    public SetAvatar() {
        super("set avatar");
        schema.add(new Argument("name", String.class, true));
        schema.add(new Argument("avatar", Character.class, true));
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        boolean result = validate(args);
        if (!result) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }
        String name = args[0];
        char avatar = args[1].charAt(0);

        Figure selected = GameSetupDatabase
                .getInstance()
                .getFigureRepository()
                .getFigureByName(name);

        if (selected == null) {
            IO.println("ERROR: No Figure of that name exists");
            return false;
        }
        selected.setAvatar(avatar);

        return true;
    }
}
