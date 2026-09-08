package game;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * .
 * @author ulprv
 */
public class InputManager {
    private final Scanner sc;
    private final List<String> userInput;

    /**
     * Creates the input manager that holds the IO Stream.
     */
    public InputManager() {
        this.sc = new Scanner(System.in);
        this.userInput = new ArrayList<>();

        userInput.add("create fighter Player 100 100 100 100 100");
        userInput.add("create fighter Robot 100 100 100 100 10");
        userInput.add("set avatar Player P");
        userInput.add("set avatar Robot R");
        userInput.add("list fighter");
        userInput.add("create card Hit melee 100 5 30");
        userInput.add("create card Throw distance 100 10 30");
        userInput.add("list card");
        userInput.add("set deck Player 0 0 0 1");
        userInput.add("set deck Robot 1 1 1 0");
        userInput.add("list deck Player");
        userInput.add("list deck Robot");
        userInput.add("start Player Robot");
        userInput.add("hand");
        userInput.add("Hit");
        userInput.add("hand");
        userInput.add("move f");
        userInput.add("hand");
        userInput.add("power-nap");
        userInput.add("chill");
        userInput.add("smack");
        userInput.add("3");
        userInput.add("28");
        userInput.add("108");
        userInput.add("109");
        userInput.add("hand");
        userInput.add("move r");
        userInput.add("quit");
    }

    /**
     * .
     * @return next player console input
     */
    public String getNextPlayerInput() {
        String input;
        if (userInput.isEmpty()) {
            input = sc.nextLine();
        } else {
            input = userInput.getFirst();
            userInput.removeFirst();
        }
        return input; //sc.nextLine();
    }

    /**
     * closes the scanner.
     */
    public void close() {
        this.sc.close();
    }
}
