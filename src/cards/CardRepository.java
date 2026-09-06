package cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * .
 *
 * @author ulprv
 */
public class CardRepository {
    private final List<Card> cards;

    /**
     * creates a card repository that holds information about any card that has been created.
     */
    public CardRepository() {
        this.cards = new ArrayList<>();
    }

    /**
     * gets the i-th created card.
     * @param i is the index of a card.
     * @return the card with the given index
     */
    public Card getCardByIndex(int i) {
        return cards.stream()
                .filter(c -> c.getIndex() == i)
                .findFirst()
                .orElse(null);
    }

    /**
     * get a card with a given name.
     * @param name of the card
     * @return card
     */
    public Card getCardByName(String name) {
        return this.cards.stream()
                .filter(c -> Objects.equals(c.getName(), name))
                .findFirst()
                .orElse(null);
    }

    /**
     * adds a new card that needs to be added to the repository.
     * @param c is a card
     */
    public void addCard(Card c) {
        this.cards.add(c);
    }

    /**
     * gets the list of all cards that were added to the repository.
     * @return the list of cards
     */
    public List<Card> getListOfCards() {
        return this.cards;
    }
}
