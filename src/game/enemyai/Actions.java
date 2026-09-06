package game.enemyai;

/**
 * Contains all options of actions the enemy AI can take.
 * @author ulprv
 */
public enum Actions {
    /**
     * .
     */
    MOVE_FORWARD("move f"),
    /**
     * .
     */
    MOVE_RETREAT("move r"),
    /**
     * .
     */
    CHILL("chill"),
    /**
     * .
     */
    SMACK("smack"),
    /**
     * .
     */
    POWER_NAP("power-nap");

    private final String getCommandString;

    Actions(String getCommandString) {
        this.getCommandString = getCommandString;
    }

    /**
     * Gets the input string that triggers a command.
     * @return command string
     */
    public String getCommandString() {
        return this.getCommandString;
    }
}
