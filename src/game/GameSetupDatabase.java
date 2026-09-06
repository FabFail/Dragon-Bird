package game;

import cards.CardRepository;
import figure.FigureRepository;

/**
 * Creates a Database of figures and cards created in the setup phase of the game.
 *
 * @author ulprv
 */
public final class GameSetupDatabase {
    private static GameSetupDatabase instance = null;
    private final FigureRepository figureRepository;
    private final CardRepository cardRepository;

    private GameSetupDatabase() {
        this.cardRepository = new CardRepository();
        this.figureRepository = new FigureRepository();
    }

    /**
     * returns or creates the instance of the DataBase.
     *
     * @return db instance
     */
    public static GameSetupDatabase getInstance() {
        if (instance == null) {
            instance = new GameSetupDatabase();
        }

        return instance;
    }

    /**
     * gets the repository of cards.
     *
     * @return card repository
     */
    public CardRepository getCardRepository() {
        return this.cardRepository;
    }


    /**
     * returns the repository of figures.
     *
     * @return figure repository
     */
    public FigureRepository getFigureRepository() {
        return figureRepository;
    }
}
