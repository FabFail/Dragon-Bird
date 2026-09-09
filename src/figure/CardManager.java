package figure;

import cards.Card;
import cards.CardType;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * manages hand and deck cards of a figure.
 *
 * @author ulprv
 */
public class CardManager {
    private static final int HAND_MAX_SIZE = 6;
    private List<Card> deckTemplate;
    private List<Card> activeDeck;
    private final List<Card> hand;
    private final String figureName;

    /**
     * creates a cardManager.
     *
     * @param name of the parent figure
     */
    public CardManager(String name) {
        this.figureName = name;
        this.deckTemplate = new ArrayList<>();
        this.activeDeck = new ArrayList<>();
        this.hand = new ArrayList<>();
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
        this.deckTemplate = newDeck;
    }

    /**
     * shuffles the deck.
     *
     * @param seed is responsible the ensure deterministic randomness in the game
     */
    public void shuffleDeck(Random seed) {
        Collections.shuffle(this.deckTemplate, seed);
        this.activeDeck = new ArrayList<>(this.deckTemplate);
    }


    /**
     * draws a card from the deck and adds it to the hand.
     */
    public void drawCardFromDeck() {
        if (this.activeDeck.isEmpty()) {
            return;
        }

        Card toDraw = this.activeDeck.getFirst();

        if (this.hand.size() < HAND_MAX_SIZE) {
            this.activeDeck.removeFirst();
            System.out.println(this.figureName + " drew " + toDraw.getName() + " from deck");
            this.hand.add(toDraw);
        }
    }

    /**
     * gives information if player has a set deck that is not empty.
     *
     * @return true if player has a deck
     */
    public boolean hasNoDeck() {
        return this.deckTemplate.isEmpty();
    }

    /**
     * prints hand of a figure.
     */
    public void printHand() {
        if (hand.isEmpty()) {
            System.out.println("-- no cards --");
            return;
        }

        for (Card c : hand) {
            if (c.getType().isCombatCard()) {
                System.out.printf("%s %d %s D:%d%n",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase(),
                        c.getValue());

            } else if (c.getType().isDefenseCard()) {
                System.out.printf("%s %d %s%n",
                        c.getName(),
                        c.getCost(),
                        c.getType().getKeyword());
            } else if (c.getType() == CardType.DAMAGE) {
                System.out.printf("%s %d %s %d%n",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase(),
                        c.getValue());
            } else if (c.getType() == CardType.REGAIN) {
                System.out.printf("%s %d %s%n",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase());
            } else {
                System.out.printf("%s %d %s %d%n",
                        c.getName(),
                        c.getCost(),
                        c.getType().toString().toLowerCase(),
                        c.getValue());
            }
        }
    }

    /**
     * Prints deck of figure => move to CardManager by injecting name into it.
     */
    public void printDeck() {

        if (activeDeck.isEmpty()) {
            IO.println("No deck for " + this.figureName);
        }
        IO.println("Deck for " + figureName + " - " + activeDeck.size() + " cards:");
        IO.println();

        for (Card c : activeDeck) {
            IO.println(c);
        }
    }

    /**
     * resets deck.
     */
    public void resetDeck() {
        this.hand.clear();
        this.activeDeck = new ArrayList<>(this.deckTemplate);
    }

}
