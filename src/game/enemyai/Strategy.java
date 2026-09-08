package game.enemyai;

import game.GameState;

/**
 * Necessary functions to ensure an AI Strategy is valid.
 * @author ulprv
 */
public interface Strategy {

    /**
     * get the next input string that can trigger a command.
     * @param g is the game state
     * @return enemy input that triggers a command
     */
    String getNextAction(GameState g);
}
