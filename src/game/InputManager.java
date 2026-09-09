package game;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * .
 *
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

        //Obliterate();
        correctFailOrder();
    }

    /**
     * .
     *
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
        System.out.println(input);
        return input; //sc.nextLine();
    }

    private void mandatoryTest() {
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
    }

    private void MyManThis() {
        userInput.add("create fighter MyMan 100 10 10 10 9");
        userInput.add("set avatar MyMan THIS");
    }

    private void activePlayerIsNull() {
        userInput.add("create fighter Me 100 10 10 10 9");
        userInput.add("create fighter Them 100 10 10 10 10");
        userInput.add("set avatar Me P");
        userInput.add("set avatar Them R");
        userInput.add("create card ExpRegain regain 100 100 1");
        userInput.add("create card Alert bas.defense 100 1");
        userInput.add("set deck Me 1");
        userInput.add("set deck Them 0");
        userInput.add("start Me Them");
        userInput.add("Alert");
    }

    private void HandJob() {
        userInput.add("create fighter Me 100 10 10 10 9");
        userInput.add("create fighter Them 100 10 10 10 10");
        userInput.add("set avatar Me P");
        userInput.add("set avatar Them R");
        userInput.add("create card CheapRegain regain 100 1 2");
        userInput.add("create card ExpRegain regain 100 100 1");
        userInput.add("set deck Me 0");
        userInput.add("set deck Them 1");
        userInput.add("start Me Them");
        userInput.add("chill");
        userInput.add("hand");
    }

    private void EnergyUp() {
        userInput.add("create fighter Hero 200 10 10 10 9");
        userInput.add("create fighter Opponent 200 10 10 10 10");
        userInput.add("set avatar Hero H");
        userInput.add("set avatar Opponent O");
        userInput.add("create card EnergyUp energy+ 100 3 2");
        userInput.add("create card Throw distance 100 6 20");
        userInput.add("create card Heal regain 100 1 5");
        userInput.add("create card Obliterate damage 100 1 500");
        userInput.add("list card");
        userInput.add("set deck Hero 3");
        userInput.add("set deck Opponent 0 1 2");
        userInput.add("start Hero Opponent");

        userInput.add("move f");
        userInput.add("smack");
        userInput.add("1");
        userInput.add("move f");
        userInput.add("smack");
        userInput.add("1");
        userInput.add("chill");
        userInput.add("smack");
        userInput.add("40");
        userInput.add("49");
        userInput.add("98");
        userInput.add("chill");
        userInput.add("Obliterate");
    }

    private void correctFailOrder() {
        userInput.add("create fighter Hero 200 10 10 10 9");
        userInput.add("create fighter Opponent 200 10 10 10 10");
        userInput.add("set avatar Hero H");
        userInput.add("set avatar Opponent O");
        userInput.add("create card Obliterate damage 70 3 300");
        userInput.add("create card Hit melee 50 1 10");
        userInput.add("create card Scam regain 5 100 1");
        userInput.add("set deck Hero 0 0 1 1");
        userInput.add("set deck Opponent 2");
        userInput.add("start Hero Opponent");

        userInput.add("move f");
        userInput.add("Hit");
    }

    private void Obliterate() {
        userInput.add("create fighter Hero 50 10 10 10 10");
        userInput.add("create fighter Level1 3 5 3 2 9");
        userInput.add("create fighter Level2 10 10 10 10 9");
        userInput.add("create fighter Level3 30 10 10 10 10");
        userInput.add("set avatar Hero H");
        userInput.add("set avatar Level1 1");
        userInput.add("set avatar Level2 2");
        userInput.add("set avatar Level3 3");
        userInput.add("create card Obliterate damage 70 3 300");
        userInput.add("create card Hit melee 50 1 10");
        userInput.add("create card Throw distance 90 1 20");
        userInput.add("create card Scam regain 5 100 1");
        userInput.add("set deck Hero 0 0 1 1 1 2 2 2");
        userInput.add("set deck Level1 3");
        userInput.add("set deck Level2 2");
        userInput.add("set deck Level3 0 2");

// Game 1: Hero vs Level1
        userInput.add("start Hero Level1");
        userInput.add("smack");
        userInput.add("25");
        userInput.add("82");
        userInput.add("137");

// Game 2: Hero vs Level2
        userInput.add("start Hero Level2");
        userInput.add("hand");
        userInput.add("Hit");

// Game 3: Hero vs Level3
        userInput.add("start Hero Level3");
        userInput.add("move f");
        userInput.add("hand");
    }

    /**
     * closes the scanner.
     */
    public void close() {
        this.sc.close();
    }
}
