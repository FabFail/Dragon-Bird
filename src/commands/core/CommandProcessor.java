package commands.core;

import cards.Card;
import commands.combat.UseCard;
import figure.Figure;
import game.GamePhase;
import game.GameState;

import java.util.Optional;

/**
 * Holds a list of commands that can be processed.
 *
 * @author ulprv
 */
public class CommandProcessor {
    private final CommandRepository repository;

    /**
     * creates a processor by giving it a list of commands.
     *
     * @param cmdRepo contains a list of commands
     */
    public CommandProcessor(CommandRepository cmdRepo) {
        this.repository = cmdRepo;
    }

    /**
     * starts processing command by matching the keyword against the commands in the repository.
     *
     * @param g        is the game state
     * @param rawInput is raw user input string
     * @return true if a command can be processed and consumes an action
     */
    public Optional<Command.ParsedCommand> parseCommand(GameState g, String rawInput) {
        Optional<Command.ParsedCommand> repoCommand = parseRepositoryCommand(rawInput);

        if(repoCommand.isPresent()) {
            return repoCommand;
        }

        Optional<Command.ParsedCommand> cardCommand = parseCardCommand(g, rawInput);
        if (cardCommand.isPresent()) {
            return cardCommand;
        }

        System.out.println("ERROR: Command not found");
        return Optional.empty();
    }

    private Optional<Command.ParsedCommand> parseRepositoryCommand(String rawInput) {
        Optional<Command> result = this.repository.matchWithKeyword(rawInput.toLowerCase());

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Command cmd = result.get();
        String[] parsedArgs = extractArguments(rawInput, cmd.getKeyword().length());
        return Optional.of(new Command.ParsedCommand(cmd, parsedArgs));
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

    private Optional<Command.ParsedCommand> parseCardCommand(GameState g, String rawInput) {
        if (g.getGamePhase() == GamePhase.SETUP) {
            return Optional.empty();
        }

        Figure activePlayer = g.getPlayerAtTurn();

        for (Card card : activePlayer.getCardManager().getHand()) {
            String cardName = card.getName();


            if (rawInput.equals(cardName)) {

                Command.ParsedCommand cardCmd = new Command.ParsedCommand(new UseCard(card), new String[0]);
                return Optional.of(cardCmd);
            }
        }

        return Optional.empty();
    }
}
