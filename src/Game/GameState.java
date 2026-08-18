package Game;

public class GameState {
    private boolean isRunning;
    private static int counter = 1;
    private int turnNumber;

    public GameState() {
        this.isRunning = true;
        this.turnNumber = counter++;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void endGame() {
        this.isRunning = false;
    }

    public int incrementGameTurn() {
        return this.turnNumber++;
    }



}
