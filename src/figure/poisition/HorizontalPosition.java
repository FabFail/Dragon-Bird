package figure.poisition;

/**
 * Contains all options for the horizontal positions of figures.
 * @author ulprv
 */
public enum HorizontalPosition {
    /**
     * .
     */
    FORWARD('f', 1),
    /**
     * .
     */
    RETREATED('r', 0);

    private static final int MAX_DISTANCE = 2;
    private final char commandFlag;
    private final int distanceModifier;


    HorizontalPosition(char flag, int distanceModifier) {
        this.commandFlag = flag;
        this.distanceModifier = distanceModifier;
    }

    /**
     * calculates the distance between two horizontal positions.
     * @param pos1 is the active players position
     * @param pos2 is the opposing players position
     * @return distance value
     */
    public static int calculateDistance(HorizontalPosition pos1, HorizontalPosition pos2) {
        return MAX_DISTANCE - pos1.distanceModifier - pos2.distanceModifier;
    }

    /**
     * transforms the input flag to a position.
     * @param flag is the input of the user
     * @return position if flag correspondents to a position
     */
    public static HorizontalPosition fromChar(char flag) {
        for (HorizontalPosition pos : values()) {
            if (pos.commandFlag == flag) {
                return pos;
            }
        }
        return null;
    }
}
