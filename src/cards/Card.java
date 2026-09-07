package cards;

/**
 * Contains information about Card.
 * @author ulprv
 */
public class Card {
    private static int counter = 0;
    private final int index;
    private final String name;
    private final CardType type;
    private int accuracy; // 0 to 100
    private int cost; // non-negative
    private int value;

    /**
     * Creates a Card.
     * @param name is the cards name
     * @param type is the type of the card
     * @param accuracy decides whether the card hits or misses
     * @param cost is the cost it needs to be used
     * @param value is the effect strength
     */
    public Card(String name, CardType type, int accuracy, int cost, int value) {
        this.index = counter++;
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.cost = cost;
        this.value = value;
    }

    /**
     * gets the name.
     * @return card name
     */
    public String getName() {
        return name;
    }

    /**
     *  get the index of the card.
     * @return order in which the card was created
     */
    public int getIndex() {
        return index;
    }

    /**
     *  get the accuracy of the card.
     * @return the accuracy between 0 (always hit) and 100 (always miss).
     */
    public int getAccuracy() {
        return this.accuracy;
    }

    /**
     * get the effect value of the card.
     * @return the effect strength of the card.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * get the type of the card.
     * @return the card type.
     */
    public CardType getType() {
        return type;
    }

    /**
     * updates a card.
     * @param accuracy decides whether the card hits or misses
     * @param cost is the cost it needs to be used
     * @param value is the effect strength
     */
    public void update(int accuracy, int cost, int value) {
        this.accuracy = accuracy;
        this.cost = cost;
        this.value = value;
    }

    /**
     * get the card cost value of the card.
     * @return card cost
     */
    public int getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        return String.format("%03d %-12.12s %-5s D:%d Acc:%d CC:%d",
                this.index,
                this.name,
                this.type.getKeyword(),
                this.value,
                this.accuracy,
                this.cost);
    }
}
