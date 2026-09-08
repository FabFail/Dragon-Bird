package game;

import figure.Figure;
import figure.FigurePhase;

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
     * ends the game.
     */
    public void endGame() {
        this.isRunning = false;
        p.updatePhase(FigurePhase.GAME_OVER);
        ai.updatePhase(FigurePhase.GAME_OVER);
        printBoard();
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
