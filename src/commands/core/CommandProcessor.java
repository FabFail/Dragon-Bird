package commands.core;

import cards.Card;
import commands.combat.UseCard;
import figure.Figure;
import game.GamePhase;
import game.GameState;

import java.util.Arrays;
import java.util.Optional;

/**
 * Holds a list of commands that can be processed.
 * @author ulprv
 */
public class CommandProcessor {
    private final CommandRepository repository;

    /**
     * creates a processor by giving it a list of commands.
     * @param cmdRepo contains a list of commands
     */
    public CommandProcessor(CommandRepository cmdRepo) {
        this.repository = cmdRepo;
    }

    /**
     * starts processing command by matching the keyword against the commands in the repository.
     * @param g is the game state
     * @param rawInput is raw user input string
     * @return true if a command can be processed and consumes an action
     */
    public boolean processCommand(GameState g, String rawInput) {
        Optional<Command> result = this.repository.matchWithKeyword(rawInput.toLowerCase());

        if (result.isPresent()) {
            return executeRepositoryCommand(g, rawInput, result.get());
        }

        if (checkCardCommand(g, rawInput)) {
            return true;
        }

        IO.println("ERROR: Command not found");
        return false;
    }

    /**
     * executes the command within its repository.
     *
     * @param g is the game state
     * @param rawInput is the raw user input
     * @param cmd is a command
     * @return true if action is consumed
     */
    private boolean executeRepositoryCommand(GameState g, String rawInput, Command cmd) {
        String[] args = extractArguments(rawInput, cmd.getKeyword().length());
        return cmd.execute(g, args);
    }

    private String[] extractArguments(String rawInput, int keywordLen) {
        // Exact match with no arguments, e.g. quit
        if (rawInput.length() == keywordLen) {
            return new String[0];
        }

        if (rawInput.length() > keywordLen && rawInput.charAt(keywordLen) == ' ') {
            String argsString = rawInput.substring(keywordLen + 1);
            return argsString.isEmpty() ? new String[0] : argsString.split(" ");
        }

        return new String[0];
    }

    private boolean checkCardCommand(GameState g, String rawInput) {
        if (g.getGamePhase() == GamePhase.SETUP) {
            return false;
        }

        Figure activePlayer = g.getPlayerAtTurn();

        for (Card card : activePlayer.getCardManager().getHand()) {
            String cardName = card.getName();
            IO.println("DEBUG " + card.getName() + " " + rawInput);

            if (rawInput.equals(cardName)) {

                UseCard cardCmd = new UseCard(card);
                return cardCmd.execute(g, new String[0]);
            }
        }

        return false;
    }
}
