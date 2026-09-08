package commands.setup;

import commands.core.Argument;
import commands.core.Command;
import figure.Figure;
import game.GameState;
import game.GameSetupDatabase;

import java.util.Arrays;

/**
 * Handles validation and execution of the command that sets a figures avatar.
 *
 * @author ulprv
 */
public class SetAvatar extends Command {

    /**
     * creates the command that sets a figures avatar.
     */
    public SetAvatar() {
        super("set avatar");
        schema.add(new Argument("name", String.class, true));
        schema.add(new Argument("avatar", String.class, true));
    }

    @Override
    protected boolean execute(GameState g, String[] args) {
        boolean result = validate(args);
        if (!result) {
            return false;
        }

        if (args[1].length() > 1) {
            System.out.println("Error: Avatar must be a single character.");
            return false;
        }
        String name = args[0];
        char avatar = args[1].charAt(0);

        Figure selected = GameSetupDatabase
                .getInstance()
                .getFigureRepository()
                .getFigureByName(name);

        if (selected == null) {
            System.out.println("ERROR: No Figure of that name exists");
            return false;
        }
        selected.setAvatar(avatar);
        System.out.println("Avatar set.");

        return true;
    }
}
