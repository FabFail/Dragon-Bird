package game.enemyai;

import game.GameState;

/**
 * Is the enemy AI that decides the next action.
 *
 * @author ulprv
 */
public class EnemyAI {
    private Strategy currentStrategy;

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
        if (g.isAIGoingOff()) {
            this.currentStrategy = new OffensiveStrategy();
            System.out.println("DEBUG: Change Strategy to Offense");
            g.changeAIToDef(false); // resets the flag to change strategy
        }

    }

    private void checkDefensiveStrategy(GameState g) {
        if (g.isAIGoingDef()) {
            this.currentStrategy = new DefensiveStrategy();
            System.out.println("DEBUG: Change Strategy to Defense");
            g.changeAIToOff(false); // resets flag to change strategy
        }
    }
}
