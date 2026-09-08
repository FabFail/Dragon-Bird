package figure;

/**
 * Contains all possible options of a figures phase.
 * @author ulprv
 */
public enum FigurePhase {
    /**
     * .
     */
    DEFENSE(" "),
    /**
     * .
     */
    ATTACK("*"),
    /**
     * .
     */
    GAME_OVER(" ");

    private final String symbol;

    FigurePhase(String s) {
        this.symbol = s;
    }

    /**
     * get the symbol that corresponds to a figures phase.
     * @return symbol
     */
    public String getSymbol() {
        return this.symbol;
    }

}
