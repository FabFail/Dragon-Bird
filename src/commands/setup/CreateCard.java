package commands.setup;

import cards.Card;
import cards.CardRepository;
import cards.CardType;
import commands.core.Argument;
import commands.core.Command;
import game.GameState;
import game.GameSetupDatabase;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Handles validation and execution of the command to create cards.
 * @author ulprv
 */
public class CreateCard extends Command {
    private static final String ASCII_PATTERN = "\\p{ASCII}";
    private static final String NON_WHITESPACES = "\\S";
    // intersection of ASCII and non-whitespace printable character
    private static final String CARD_NAME_REGEX = "^["
            + ASCII_PATTERN + "&&"
            + NON_WHITESPACES + "]*$";

    private static final Pattern CARD_NAME_PATTERN = Pattern.compile(CARD_NAME_REGEX);


    /**
     * Creates a card.
     */
    public CreateCard() {
        super("create card");
        schema.add(new Argument("name", String.class, true));
        schema.add(new Argument("type", CardType.class, true));
        schema.add(new Argument("accuracy", Integer.class, true));
        schema.add(new Argument("cost", Integer.class, true));
    }


    @Override
    protected boolean execute(GameState g, String[] args) {
        if (!validate(args)) {
            IO.println("DEBUG: Stop Execution and return false");
            return false;
        }

        String name = args[0];
        CardType type = CardType.fromString(args[1]);
        int accuracy = Integer.parseInt(args[2]);
        int cost = Integer.parseInt(args[3]);
        // safe parse as not every card has a value
        int value = (args.length == 5) ? Integer.parseInt(args[4]) : 0;

        if (!CARD_NAME_PATTERN.matcher(name).matches()) {
            IO.println("ERROR: Card name can only contain regular ASCII symbols");
            return false;
        }

        if (cost < 0) {
            IO.println("ERROR: Card Cost must be non-negative!");
        }

        if (accuracy < 0 || accuracy > 100) {
            IO.println("ERROR: Accuracy must be between 0 and 100!");
        }

        if (value < 1) {
            IO.println("ERROR: Value must be a positive number!");
        }


        CardRepository cardRepo = GameSetupDatabase.getInstance().getCardRepository();

        Card exists = cardRepo.getCardByName(name);

        if (exists == null) {
            cardRepo.addCard(new Card(name, type, accuracy, cost, value));
            IO.println(String.format("Card %s created", name));
        } else {
            exists.update(accuracy, cost, value);
            IO.println(String.format("Card %s updated", name));
        }
        return true;
    }


    @Override
    public boolean validate(String[] args) {
        if (args.length < schema.size()) {
            IO.println("ERROR: too few arguments");
            return false;
        }

        if (CardType.isCardType(args[1])) {
            IO.println("ERROR: CardType is wrong");
            return false;
        }

        CardType type = CardType.fromString(args[1]);
        int expectedTotalArgs = 4 + type.getExtraArgsCount();

        if (args.length != expectedTotalArgs) {
            IO.println("ERROR: Expected total args of " + expectedTotalArgs + " but got " + args.length);
            return false;
        }

        // validate first 4 schema arguments
        if (!super.validate(Arrays.copyOfRange(args, 0, 4))) {
            IO.println("DEBUG: Super validation does not work");
            return false;
        }

        if (type.getExtraArgsCount() == 1) {
            if (!isInteger(args[4])) {
                System.out.println("ERROR: Extra argument must be an integer");
                return false;
            }
        }
        return true;
    }
}