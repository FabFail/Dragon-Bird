package Commands;

import Game.GameState;

public class Quit extends Command{
    protected Quit() {
        super("quit");
    }

    @Override
    public void execute(GameState g) {
        g.endGame();
    }
}
