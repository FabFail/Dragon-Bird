package figure;

import figure.poisition.HorizontalPosition;
import figure.poisition.VerticalPosition;
import java.util.Random;

/**
 * Holds all information of a figure.
 *
 * @author ulprv
 */
public class Figure {
    private static final char DEFAULT_AVATAR = 'A';
    private static int counter = 0;
    private final String name;
    private Character avatar;

    private final int index;

    private FigurePhase phase;

    private HorizontalPosition hPos;
    private VerticalPosition vPos;
    private final CardManager cardManager;
    private final CombatDataManager combatDataManager;
    private final StatManager statManager;


    /**
     * Creates a figure.
     *
     * @param name of the figure
     * @param fd   stats of a figure
     */
    public Figure(String name, FigureData fd) {
        this.index = counter++;
        this.name = name;
        this.avatar = DEFAULT_AVATAR;

        this.vPos = VerticalPosition.GROUNDED;
        this.hPos = HorizontalPosition.RETREATED;

        this.phase = FigurePhase.DEFENSE; // default value;
        this.cardManager = new CardManager(this.name);
        this.combatDataManager = new CombatDataManager();
        this.statManager = new StatManager(fd);

    }

    /**
     * Shuffles deck and draws card to ensure the figures are ready for the game to start.
     *
     * @param rnd is the seeded random object of the game
     */
    public void ready(Random rnd) {
        this.cardManager.shuffleDeck(rnd);
        // draw first three cards
        this.cardManager.drawCardFromDeck();
        this.cardManager.drawCardFromDeck();
        this.cardManager.drawCardFromDeck();
    }

    /**
     * returns module that handles all card related data.
     * @return card manager
     */
    public CardManager getCardManager() {
        return this.cardManager;
    }

    /**
     * provides the module that handles all stat changes.
     * @return stat manager
     */
    public StatManager getStatManager() {
        return this.statManager;
    }

    /**
     * set the figures horizontal position.
     * @param newPos new position
     */
    public void setHorizontalPosition(HorizontalPosition newPos) {
        this.hPos = newPos;
    }

    /**
     * set the figures vertical position.
     * @param newPos new position
     */
    public void setVerticalPosition(VerticalPosition newPos) {
        this.vPos = newPos;
    }

    /**
     * updates the managers after each action.
     */
    public void tick() {
        this.statManager.tick();
        this.combatDataManager.resetCardEffects();
    }

    /**
     * provides the module concerned about combat data.
      * @return combat data manager
     */
    public CombatDataManager getCombatDataManager() {
        return this.combatDataManager;
    }


    /**
     * .
     * @param avatar of figure
     */
    public void setAvatar(char avatar) {
        this.avatar = avatar;
    }

    /**
     * .
     * @return avatar
     */
    public char getAvatar() {
        return this.avatar;
    }

    /**
     * changes a figures phase.
     * @param phase decides the action a figure can take
     */
    public void updatePhase(FigurePhase phase) {
        this.phase = phase;
    }

    /**
     * get horizontal position.
     * @return horizontal position
     */
    public HorizontalPosition getHorizontalPosition() {
        return this.hPos;
    }

    /**
     * get vertical position.
     * @return vertical position
     */
    public VerticalPosition getVerticalPosition() {
        return this.vPos;
    }

    /**
     * provides a figures current phase.
     * @return phase
     */
    public FigurePhase getPhase() {
        return this.phase;
    }

    /**
     * get the name of the figure.
     * @return name
     */
    public String getName() {
        return this.name;
    }


    /**
     * is for formatting of the board.
     * @return health percentage
     */
    public float getHealthPercentage() {
        return (float) this.statManager.getCurrentHP() / (float) this.statManager.getFigureData().health();
    }


    @Override
    public String toString() {
        FigureData fd = statManager.getFigureData();
        return String.format("%02d %-10.10s HP:%d Ene:%d Pow:%d Def:%d Spd:%d",
                this.index,
                this.name,
                fd.health(),
                fd.energy(),
                fd.power(),
                fd.defense(),
                fd.speed());
    }


    /**
     * handles transition to attack phase.
     */
    public void startOffensePhase() {
        if (this.phase == FigurePhase.ATTACK) {
            statManager.updateCardCost(3);
            cardManager.drawCardFromDeck();
        }
    }

    /**
     * handles transition to defense phase.
     */
    public void endDefensePhase() {
        if (this.cardManager.getHand().size() == 6) {
            this.cardManager.getHand().removeFirst();
        }
    }


}
