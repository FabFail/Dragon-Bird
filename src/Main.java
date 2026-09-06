import game.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
/**
 * Entry point of the program.
 *
 * @param args contains the seed for the game
 * @author ulprv
 */
void main(String[] args) {
    if (args.length != 1) {
        System.out.println("Error, invalid amount of arguments");
        return;
    }

    int seed = Integer.parseInt(args[0]);
    Game g = new Game(seed);
    g.run();


    try {
        seed = Integer.parseInt(args[0]);
        Game game = new Game(seed);
        game.run();
    } catch (NumberFormatException e) {
        System.out.println("Error, seed must be a number");

    }

}
