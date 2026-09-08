package commands.core;

import cards.CardType;
import game.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * base class of a command.
 *
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
     *
     * @param keyword triggering the command
     */
    protected Command(String keyword) {
        this.keyword = keyword;
        this.schema = new ArrayList<>();
    }

    /**
     * get the keyword that is triggering commands.
     *
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * get the schema of arguments a command needs.
     *
     * @return schema
     */
    public List<Argument> getSchema() {
        return schema;
    }

    /**
     * executes a command.
     *
     * @param g    state of the game containing all necessary data
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
            Class<?> t = schema.get(i).type();

            if (t == Integer.class) {
                if (isNotInteger(args[i])) {
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
     *
     * @param s is player Input
     * @return true if string is integer
     */
    public boolean isNotInteger(String s) {
        try {
            Integer.parseInt(s);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * To ensure the command can be executed and satisfies all requirements.
     *
     * @param g    game state
     * @param args parsed arguments
     * @return true if it can be executed safely
     */
    public boolean preValidate(GameState g, String[] args) {
        return true;
    }

    /**
     * Wrapper of command with already parsed arguments.
     *
     * @param cmd  parsed command
     * @param args parsed arguments
     */
    public record ParsedCommand(Command cmd, String[] args) {

        /**
         * Executes the wrapper.
         *
         * @param g current state of the game
         * @return true if action was consumed
         */
        public boolean execute(GameState g) {
            return cmd.execute(g, args);
        }

        /**
         * Validates the wrapper by passing the parsed arguments and the game state to command.
         *
         * @param g current state of the game
         * @return true if command is valid
         */
        public boolean preValidate(GameState g) {
            return cmd.preValidate(g, args);
        }

        /**
         * Gets the name of the action.
         * @return keyword
         */
        public String getKeyWord() {
            return cmd.getKeyword();
        }

        /**
         * unwraps parsed command to get access to concrete functions.
         *
         * @return unwrapped command
         */
        public Command getCommand() {
            return this.cmd();
        }

    }
}