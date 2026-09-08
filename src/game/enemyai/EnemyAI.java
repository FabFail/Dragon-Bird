package game.enemyai;

import game.GameState;

/**
 * Is the enemy AI that decides the next action.
 *
 * @author ulprv
 */
public class EnemyAI {
    private Strategy currentStrategy;
    private boolean aiDefChange = false;
    private boolean aiOffChange = false;

    /**
     * Creates the enemy AI and sets its default behavior.
     */
    public EnemyAI() {
        this.currentStrategy = new OffensiveStrategy();
    }

    /**
     * get next action of the enemyAI.
     *
     * @param g is the game state
     * @return enemy input to trigger commands
     */
    public String getNextAction(GameState g) {
        checkStrategy(g);

        return currentStrategy.getNextAction(g);
    }

    private void checkStrategy(GameState g) {
        if (currentStrategy.getClass() == DefensiveStrategy.class) {
            System.out.println("DEBUG: Current Strategy is Defense");
            checkOffensiveStrategy(g);

        } else {
            System.out.println("DEBUG: Current Strategy is Offense");
            checkDefensiveStrategy(g);
        }
    }

    private void checkOffensiveStrategy(GameState g) {
        if (isAIGoingOff()) {
            this.currentStrategy = new OffensiveStrategy();

            changeAIToDef(false); // resets the flag to change strategy
        }

    }

    private void checkDefensiveStrategy(GameState g) {
        if (isAIGoingDef()) {
            this.currentStrategy = new DefensiveStrategy();
            changeAIToOff(false); // resets flag to change strategy
        }
    }

    /**
     * gets information about whether enemy needs to change its strategy to offensive.
     *
     * @return true if AI should change its strategy
     */
    public boolean isAIGoingOff() {
        return aiDefChange;
    }

    /**
     * sets information about whether enemy needs to change its strategy to offensive.
     *
     * @param aiDefChange determines whether AI should change its strategy
     */
    public void changeAIToOff(boolean aiDefChange) {
        this.aiDefChange = aiDefChange;
    }

    /**
     * gets information about whether enemy needs to change its strategy to defensive.
     *
     * @return true if AI should change its strategy
     */
    public boolean isAIGoingDef() {
        return this.aiOffChange;
    }

    /**
     * sets information about whether enemy needs to change its strategy to defensive.
     *
     * @param aiOffChange determines whether AI should change its strategy
     */
    public void changeAIToDef(boolean aiOffChange) {
        this.aiOffChange = aiOffChange;
    }
}
