package Commands;

import Game.GameState;

public abstract class Command {
    private final String keyword;

    protected Command(String keyword){
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public abstract void execute(GameState g);
}
