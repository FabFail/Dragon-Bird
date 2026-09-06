package game.enemyai;

import commands.core.Command;
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
            checkOffensiveStrategy(g);
        } else {
            checkDefensiveStrategy(g);
        }
    }

    private void checkOffensiveStrategy(GameState g) {
        if (g.isAIGoingOff()) {
            this.currentStrategy = new OffensiveStrategy();
            g.changeAIToDef(false); // resets the flag to change strategy
        }

    }

    private void checkDefensiveStrategy(GameState g) {
        if (g.isAIGoingDef()) {
            this.currentStrategy = new DefensiveStrategy();
            g.changeAIToOff(false); // resets flag to change strategy
        }
    }
}
