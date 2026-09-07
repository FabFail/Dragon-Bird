import game.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
/**
 * Entry point of the program.
 *
 * @param args contains the seed for the game
 * @author ulprv
 */
void main(String[] args) {
    //if (args.length != 1) {
    //    System.out.println("ERROR: invalid amount of arguments");
    //    return;
    //}
    int seed = 69;
    Game game = new Game(seed);
    game.run();
    /**
    try {
        int seed = Integer.parseInt(args[0]);
        Game game = new Game(seed);
        game.run();
    } catch (NumberFormatException e) {
        System.out.println("ERROR: seed must be a number");

    }
*/
}
