package game;

import figure.Figure;
import game.enemyai.EnemyAI;

import java.util.Random;

/**
 * Contains all Data related to the ongoing game.
 *
 * @author ulprv
 */
public class GameState {
    private boolean isRunning;
    private int turnNumber;
    private GamePhase phase;
    private final Random rnd;

    private Figure p;
    private Figure ai;
    private Figure playerAtTurn; // tracks figure that is taking action

    private final Board board;
    private EnemyAI enemyAI = new EnemyAI();
    private boolean aiDefChange = false;
    private boolean aiOffChange = false;

    /**
     * Creates the state of the game.
     *
     * @param rnd seeded random object to determine randomness in the game.
     */
    public GameState(Random rnd) {
        this.isRunning = true;
        this.turnNumber = 0;
        this.phase = GamePhase.SETUP;
        this.board = new Board();
        this.enemyAI = new EnemyAI();
        this.rnd = rnd;
    }

    /**
     * checks if game is still running.
     *
     * @return true if still running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Terminates the entire application e.g. via Quit command
     */
    public void quitGame() {
        this.isRunning = false;
    }

    /**
     * End current combat match.
     */
    public void endMatch() {
        this.phase = GamePhase.SETUP;
        this.turnNumber = 0;
        this.p = null;
        this.ai = null;
        this.enemyAI = new EnemyAI();
    }


    /**
     * get the player figure.
     *
     * @return player
     */
    public Figure getPlayer() {
        return this.p;
    }

    /**
     * gets the AI figure.
     *
     * @return AI
     */
    public Figure getAi() {
        return this.ai;
    }

    /**
     * get current turn number.
     *
     * @return current turn number
     */
    public int getTurnNumber() {
        return this.turnNumber;
    }

    /**
     * get the current phase of the game.
     *
     * @return game phase
     */
    public GamePhase getGamePhase() {
        return this.phase;
    }

    /**
     * prints the board.
     */
    public void printBoard() {
        this.board.printBoard(this.p, this.ai);
    }

    /**
     * Start Game and sets up player if the game hasn't started yet.
     *
     * @param playerFig is the figure of the player
     * @param aiFig     is the figure of the AI
     */
    public void startGame(Figure playerFig, Figure aiFig) {
        if (this.phase == GamePhase.COMBAT) {
            IO.println("ERROR: Game is already running");
            return;
        }

        this.phase = GamePhase.COMBAT;

        this.p = playerFig;
        this.ai = aiFig;

        p.ready(this.rnd);
        ai.ready(this.rnd);
    }

    /**
     * start the next turn.
     */
    public void nextTurn() {
        turnNumber++;
        IO.println(String.format("--- Turn %d ---", turnNumber));
    }

    /**
     * set the figure that is currently taking an action.
     *
     * @param f that is doing an action.
     */
    public void setActivePlayer(Figure f) {
        this.playerAtTurn = f;
    }

    /**
     * get the figure that is currently taking an action.
     *
     * @return the Figure of Player that is currently choosing their action
     */
    public Figure getPlayerAtTurn() {
        return this.playerAtTurn;
    }

    /**
     * get the figure that is currently not taking an action.
     *
     * @return the Figure that is currently not at turn.
     */
    public Figure getPassiveFigure() {
        return playerAtTurn == p ? ai : p;
    }

    /**
     * rolls the random object for accuracy.
     *
     * @return roll value between 0 and 99
     */
    public int getAccuracyRoll() {
        return this.rnd.nextInt(0, 100);
    }

    /**
     * draw a new whole number.
     *
     * @return roll value between 1 and 99
     */
    public int drawWholeNumber() {
        return this.rnd.nextInt(1, 100);
    }

    /**
     * Grants access to the EnemyAI that decides over actions.
     * @return enemy AI
     */
    public EnemyAI getEnemyAI() {
        return this.enemyAI;
    }


}
