package Commands;

import java.util.*;

/**
 * TODO: Maybe I need a CommandFactory that ensures there is no other command using the same keyword
 */
public final class CommandRepository {
    ArrayList<Command> commands = new ArrayList<>();

    public CommandRepository() {
        this.commands.add(new CreateCard());
        this.commands.add(new CreateFighter());
        this.commands.add(new ListCard());
        this.commands.add(new ListDeck());
        this.commands.add(new ListFighter());
        this.commands.add(new Quit());
        this.commands.add(new SetAvatar());
        this.commands.add(new SetDeck());
        this.commands.add(new Start());

        commands.stream().forEach(c -> System.out.println("Added command: " + c.getKeyword()));
    }


    /**
     *
     * @param w
     * @return Command if in repository else null
     */
    public Optional<Command> find (String w) {
       return commands.stream().filter(c -> Objects.equals(c.getKeyword(), w)).findFirst();
    }


}
