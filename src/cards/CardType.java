package cards;

import figure.FigurePhase;

/**
 * .
 *
 * @author ulprv
 */
public enum CardType {
    /**
     * .
     */
    MELEE("melee", 1, UsablePhase.ATTACK),
    /**
     * .
     */
    DISTANCE("distance", 1, UsablePhase.ATTACK),
    /**
     * .
     */
    DODGE("dodge", 0, UsablePhase.DEFENSE),
    /**
     * .
     */
    BLOCK("block", 0, UsablePhase.DEFENSE),
    /**
     * .
     */
    GEN_DEFENSE("gen.defense", 0, UsablePhase.DEFENSE),
    /**
     * .
     */
    BAS_DEFENSE("bas.defense", 0, UsablePhase.DEFENSE),
    /**
     * .
     */
    DAMAGE("damage", 1, UsablePhase.ANY),
    /**
     * .
     */
    REGAIN("regain", 1, UsablePhase.ANY),

    // State Plus
    /**
     * .
     */
    ENERGY_PLUS("energy+", 1, UsablePhase.ANY),
    /**
     * .
     */
    DEFENSE_PLUS("defense+", 1, UsablePhase.ANY),
    /**
     * .
     */
    POWER_PLUS("power+", 1, UsablePhase.ANY),
    /**
     * .
     */
    SPEED_PLUS("speed+", 1, UsablePhase.ANY),

    // State Minus
    /**
     * .
     */
    ENERGY_MINUS("energy-", 1, UsablePhase.ANY),
    /**
     * .
     */
    DEFENSE_MINUS("defense-", 1, UsablePhase.ANY),
    /**
     * .
     */
    POWER_MINUS("power-", 1, UsablePhase.ANY),
    /**
     * .
     */
    SPEED_MINUS("speed-", 1, UsablePhase.ANY);

    private final String keyword;
    private final int extraArgsCount;
    private final UsablePhase usablePhase;

    CardType(String keyword, int extraArgsCount, UsablePhase usablePhase) {
        this.keyword = keyword;
        this.extraArgsCount = extraArgsCount;
        this.usablePhase = usablePhase;
    }


    /**
     * .
     * @return true if the card is classified as a combat card.
     */
    public boolean isCombatCard() {
        return this == CardType.MELEE
                || this == CardType.DISTANCE;
    }

    /**
     * .
     * @return  true if the card is classified as a defense card.
     */
    public boolean isDefenseCard() {
        return this == CardType.DODGE
                || this == CardType.BLOCK
                || this == CardType.BAS_DEFENSE
                || this == CardType.GEN_DEFENSE;
    }

    /**
     * .
     * @return  true if the card is classified as an effect card.
     */
    public boolean isEffectCard() {
        return !isDefenseCard() && !isCombatCard();
    }

    /**
     *  get the input card type keyword.
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * gets the argument count of a given card type.
     * @return argument count of the card.
     */
    public int getExtraArgsCount() {
        return extraArgsCount;
    }

    /**
     * checks whether a given card is a keyword of a card type.
     * @param s keyword input
     * @return true if the input as a keyword
     */
    public static boolean isCardType(String s) {
        return fromString(s) == null;
    }

    /**
     * translates a String keyword to its corresponding card type.
     * @param typeStr is the keyword string of a card type
     * @return card type if input matches a keyword is successful
     */
    public static CardType fromString(String typeStr) {
        if (typeStr == null) {
            return null;
        }
        for (CardType type : values()) {
            if (type.keyword.equalsIgnoreCase(typeStr)) {
                return type;
            }
        }
        return null;
    }

    /**
     * checks if a card can be used in a certain phase.
     * @param phase of a figure
     * @return true if a card can be used in a certain phase
     */
    public boolean isUsableIn(FigurePhase phase) {
        return this.usablePhase.allows(phase);
    }

    /**
     * Contains the options in which phases a card can be usable in.
     */
    public enum UsablePhase {
        /**
         * .
         */
        ATTACK,
        /**
         * .
         */
        DEFENSE,
        /**
         * .
         */
        ANY;

        /**
         * check whether a phase accepts a certain card category.
         * @param phase of the figure which is currently doing an action
         * @return true if the card is allowed during the figures phase.
         */
        public boolean allows(FigurePhase phase) {
            if (this == ANY) {
                return true;
            }
            return (this == ATTACK && phase == FigurePhase.ATTACK)
                    || (this == DEFENSE && phase == FigurePhase.DEFENSE);
        }
    }
}
