package game;


import java.util.Scanner;

/**
 * .
 * @author ulprv
 */
public class InputManager {
    private final Scanner sc;

    /**
     * Creates the input manager that holds the IO Stream.
     */
    public InputManager() {
        this.sc = new Scanner(System.in);
    }

    /**
     * .
     * @return next player console input
     */
    public String getNextPlayerInput() {
        return sc.nextLine();
    }

    /**
     * closes the scanner.
     */
    public void close() {
        this.sc.close();
    }
}
