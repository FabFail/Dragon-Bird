package figure;

import cards.Card;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * manages hand and deck cards of a figure.
 * @author ulprv
 */
public class CardManager {
    private static final int DECK_MAX_SIZE = 20;
    private static final int HAND_MAX_SIZE = 6;
    private List<Card> deck;
    private final List<Card> hand;
    private final String figureName;

    /**
     * creates a cardManager.
     *
     * @param name of the parent figure
     */
    public CardManager(String name) {
        this.figureName = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
    }

    /**
     * get the figures deck.
     *
     * @return a list of cards that compile the deck
     */
    public List<Card> getDeck() {
        return this.deck;
    }

    /**
     * get the figures hand.
     *
     * @return a list of cards that compile the hand
     */
    public List<Card> getHand() {
        return this.hand;
    }

    /**
     * takes a list of cards and sets it as the new deck if requirements are met.
     *
     * @param newDeck is a list of card that will overwrite the old deck.
     */
    public void setDeck(List<Card> newDeck) {
        if (newDeck.size() > DECK_MAX_SIZE) {
            IO.println("ERROR: Deck can only hold up to 20 cards");
            return;
        }

        this.deck = newDeck;
    }

    /**
     * shuffles the deck.
     *
     * @param seed is responsible the ensure deterministic randomness in the game
     */
    public void shuffleDeck(Random seed) {
        Collections.shuffle(this.deck, seed);
    }


    /**
     * draws a card from the deck and adds it to the hand.
     */
    public void drawCardFromDeck() {
        if (this.deck.isEmpty()) {
            return;
        }

        Card toDraw = this.deck.getFirst();

        if (this.hand.size() < HAND_MAX_SIZE) {
            this.deck.removeFirst();
            this.hand.add(toDraw);
        }
    }

    /**
     * gives information if player has a set deck that is not empty.
     * @return true if player has a deck
     */
    public boolean hasNoDeck() {
        return this.deck.isEmpty();
    }

    /**
     * prints hand of a figure.
     */
    public void printHand() {
        if (hand.isEmpty()) {
            IO.println("-- no cards --");
            return;
        }

        for (Card c : hand) {
            if (!c.getType().isDefenseCard()) {
                IO.println(String.format("%s %d %s D:%d",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase(),
                        c.getValue()));

            } else {
                IO.println(String.format("%s %d %s",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase()));
            }
        }
    }

    /**
     * Prints deck of figure => move to CardManager by injecting name into it.
     */
    public void printDeck() {

        if (deck.isEmpty()) {
            IO.println("No deck for " + this.figureName);
        }
        IO.println("Deck for " + figureName + " - " + deck.size() + " cards:");
        IO.println();

        for (Card c : deck) {
            IO.println(c);
        }
    }

}
