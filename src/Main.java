import Game.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public static void main(String[] args) {
    if(args.length != 1) {
        System.out.println("Error, invalid amount of arguments");
        //return;
    }

    System.out.println("Default seed 69");
    int seed = 69; // Integer.parseInt(args[0]);
    Game g = new Game(seed);
    g.run();

    /**
    try{
        int seed = Integer.parseInt(args[0]);
        Game g = new Game(seed);
        g.run();
    } catch (Exception e) {
    }
     */
}
