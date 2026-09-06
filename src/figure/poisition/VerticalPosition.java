package figure.poisition;

/**
 * Contains all options for the vertical positions of figures.
 * @author ulprv
 */
public enum VerticalPosition {
    /**
     * .
     */
    GROUNDED('l'),
    /**
     * .
     */
    ASCENDED('a');

    private final char commandFlag;

    VerticalPosition(char flag) {
        this.commandFlag = flag;
    }

    /**
     * transforms the input flag to a position.
     * @param flag is the input of the user
     * @return position if flag correspondents to a position
     */
    public static VerticalPosition fromChar(char flag) {
        for (VerticalPosition pos : values()) {
            if (pos.commandFlag == flag) {
                return pos;
            }
        }
        return null;
    }
}
