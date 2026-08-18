package Game;

import java.util.Scanner;

public class InputManager {
    private final Scanner sc;

    public InputManager() {
        this.sc = new Scanner(System.in);
    }

    public String getNextPlayerInput(){
        System.out.println("Please enter command");

        return sc.nextLine();
    }

    public void close() {
        this.sc.close();
    }
}
