package commands.core;

import cards.CardType;
import game.GameState;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * base class of a command.
 * @author ulprv
 */
public abstract class Command {
    /**
     * optional schema of parameters.
     */
    protected final List<Argument> schema;
    private final String keyword;


    /**
     * creates a command of a given keyword and schema.
     * @param keyword triggering the command
     */
    protected Command(String keyword) {
        this.keyword = keyword;
        this.schema = new ArrayList<>();
    }

    /**
     * get the keyword that is triggering commands.
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * get the schema of arguments a command needs.
     * @return schema
     */
    public List<Argument> getSchema() {
        return schema;
    }

    /**
     * executes a command.
     * @param g state of the game containing all necessary data
     * @param args list of arguments that need to match the schema
     * @return true if action is consumed
     */
    protected abstract boolean execute(GameState g, String[] args);

    /**
     * validates the input arguments against the schema.
     *
     * @param args is the input of the player
     * @return true if input matches the command schema
     */
    public boolean validate(String[] args) {
        // check amount of arguments provided
        int expectedArgumentCount = schema.size();
        if (expectedArgumentCount != args.length) {
            System.out.println("ERROR: Too many or too few arguments!");
            return false;
        }

        // prevalidate to ensure conversion will be successful
        for (int i = 0; i < schema.size(); i++) {
            // get type of schema
            Type t = schema.get(i).type();

            if (t == Integer.class) {
                if (!isInteger(args[i])) {
                    return false;
                }
            }
            if (t == CardType.class) {
                if (CardType.isCardType(args[i])) {
                    return false;
                }
            }

            if (t == Character.class) {
                if (args[i].length() != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * checks if a string can be converted to an integer.
     * @param s is player Input
     * @return true if string is integer
     */
    public boolean isInteger(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}