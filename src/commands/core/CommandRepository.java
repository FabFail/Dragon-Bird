package commands.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * is a repository of commands.
 * @author ulprv
 */
public class CommandRepository {
    private final List<Command> commands;

    /**
     * creates a new repository of commands.
     */
    public CommandRepository() {
        this.commands = new ArrayList<>();
    }

    /**
     * adds a command to the repository.
     *
     * @param c is a command
     */
    public void register(Command c) {
        for (Command command : commands) {
            if (Objects.equals(command.getKeyword(), c.getKeyword())) {
                return;
            }
        }
        commands.add(c);
    }

    /**
     * matches the user input with commands in the repository.
     * @param rawInput is the raw user input
     * @return Command if found
     */
    public Optional<Command> matchWithKeyword(String rawInput) {
        return commands.stream().filter(c -> rawInput.startsWith(c.getKeyword())).findFirst();
    }

}
