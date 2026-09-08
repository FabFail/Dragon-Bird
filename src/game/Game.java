package game;


import commands.core.Command;
import commands.core.CommandProcessor;
import commands.core.CommandRepository;

import commands.setup.ListCard;
import commands.setup.CreateFighter;
import commands.setup.CreateCard;
import commands.setup.ListDeck;
import commands.setup.SetDeck;
import commands.setup.Start;
import commands.setup.Quit;
import commands.setup.ListFighter;
import commands.setup.SetAvatar;
import commands.combat.Hand;
import commands.combat.PowerNap;
import commands.combat.Smack;
import commands.combat.Chill;
import commands.combat.Move;
import figure.Figure;
import figure.FigurePhase;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Handles sequence of the Game.
 *
 * @author ulprv
 */
public final class Game {
    private final GameState g;

    private CommandProcessor setupCmdProcessor;
    private CommandProcessor combatCmdProcessor;
    private final InputManager in;


    /**
     * Creates the Game with all its subsystems.
     *
     * @param seed that determines random behavior.
     */
    public Game(int seed) {
        Random rnd = new Random(seed);
        this.g = new GameState(rnd);
        this.in = new InputManager();
        initCommandProcessors();

    }

    /**
     * Initializes a setup and combat command processors and provides them with a repository of commands
     */
    private void initCommandProcessors() {
        CommandRepository setupRepo = new CommandRepository();
        setupRepo.register(new Quit());
        setupRepo.register(new CreateFighter());
        setupRepo.register(new Start());
        setupRepo.register(new ListFighter());
        setupRepo.register(new SetAvatar());
        setupRepo.register(new CreateCard());
        setupRepo.register(new ListCard());
        setupRepo.register(new SetDeck());
        setupRepo.register(new ListDeck());

        CommandRepository combatRepo = new CommandRepository();
        combatRepo.register(new Hand());
        combatRepo.register(new Smack(this.in));
        combatRepo.register(new PowerNap());
        combatRepo.register(new Move());
        combatRepo.register(new Chill());
        combatRepo.register(new Quit());

        this.setupCmdProcessor = new CommandProcessor(setupRepo);
        this.combatCmdProcessor = new CommandProcessor(combatRepo);

    }

    /**
     * Run the Game instance to enter Setup Phase.
     */
    public void run() {
        try {
            printSetupCommand();
            while (this.g.isRunning()) {
                if (this.g.getGamePhase() == GamePhase.SETUP) {
                    runSetupLoop();
                }
                if (this.g.isRunning() && this.g.getGamePhase() == GamePhase.COMBAT) {
                    runCombatLoop();
                }
            }
        } finally {
            this.in.close();
        }
    }

    private void printSetupCommand() {
        System.out.println("Use one of the following commands: create fighter, "
                + "create card, "
                + "list fighter, "
                + "list card, "
                + "set avatar, "
                + "set deck, "
                + "list deck, "
                + "start, "
                + "quit");
    }

    /**
     * Handles all actions before the main game loop is starting
     */
    private void runSetupLoop() {
        while (this.g.getGamePhase() == GamePhase.SETUP && g.isRunning()) {
            String input = this.in.getNextPlayerInput();

            Optional<Command.ParsedCommand> cmd = setupCmdProcessor.parseCommand(g, input);
            cmd.ifPresent(parsedCommand -> parsedCommand.execute(g));
        }
    }

    /**
     * Handles sequence of combat's phase game loop
     */
    private void runCombatLoop() {
        while (g.isRunning() && g.getGamePhase() == GamePhase.COMBAT) {
            g.nextTurn();

            boolean playerIsFaster = g.getPlayer().getStatManager().getSpeed() > g.getAi().getStatManager().getSpeed();
            Figure fastFigure = playerIsFaster ? g.getPlayer() : g.getAi();
            Figure slowFigure = playerIsFaster ? g.getAi() : g.getPlayer();

            // first half phase
            turnSequence(fastFigure, slowFigure);
            if (!g.isRunning() || g.getGamePhase() != GamePhase.COMBAT) {
                break;
            }

            // second half phase
            turnSequence(slowFigure, fastFigure);
            if (!g.isRunning() || g.getGamePhase() != GamePhase.COMBAT) {
                break;
            }
        }
    }

    /**
     * handles turn sequence.
     *
     * @param attacker is the figure that is attacking during the current action
     * @param defender is the figure that is defending during the current action
     */
    private void turnSequence(Figure attacker, Figure defender) {
        attacker.updatePhase(FigurePhase.ATTACK);
        attacker.startOffensePhase();

        defender.updatePhase(FigurePhase.DEFENSE);

        g.printBoard();

        // Get actions first
        g.setActivePlayer(attacker);
        FigureAction attackerAction = getFigureAction(attacker);
        g.setActivePlayer(defender);
        FigureAction defenderAction = getFigureAction(defender);
        if (attackerAction == null || defenderAction == null) {
            return;
        }


        System.out.println("OK.");

        if (attackerAction.figure() == g.getPlayer() && Objects.equals(attackerAction.cmd().getKeyWord(), "smack")) {
            g.setActivePlayer(attacker);
            Smack smackCommand = (Smack) attackerAction.cmd().getCommand();
            smackCommand.playMinigame(g);
        }


        System.out.println(attacker.getName() + "'s attack: " + attackerAction.cmd().getKeyWord());
        System.out.println(defender.getName() + "'s defense: " + defenderAction.cmd().getKeyWord());

        g.setActivePlayer(defenderAction.figure());
        defenderAction.cmd().execute(g);
        defender.tick();

        g.setActivePlayer(attackerAction.figure());
        attackerAction.cmd().execute(g);
        attacker.tick();

        defender.endDefensePhase();
        checkGameEnd();
    }


    private boolean validateAction(FigureAction figureAction) {
        if (figureAction == null || figureAction.cmd() == null) {
            return false;
        }

        g.setActivePlayer(figureAction.figure());
        return figureAction.cmd().preValidate(g);
    }

    private FigureAction getFigureAction(Figure actor) {
        if (actor == g.getAi()) {
            return getAIAction();
        } else {
            return getPlayerAction();
        }
    }

    private FigureAction getAIAction() {
        String enemyInput = g.getEnemyAI().getNextAction(g);
        Optional<Command.ParsedCommand> result = combatCmdProcessor.parseCommand(g, enemyInput);
        return result.map(parsedCommand -> new FigureAction(g.getAi(), parsedCommand)).orElse(null);
    }

    private FigureAction getPlayerAction() {
        if (g.getPlayer().getPhase() == FigurePhase.ATTACK) {
            System.out.println(g.getPlayer().getName() + " chooses an attack move.");
        } else {
            System.out.println(g.getPlayer().getName() + " chooses a defense move.");
        }
        System.out.printf("Available Card Cost: %d\n\n%n", g.getPlayer().getStatManager().getCardCost());


        while (g.isRunning()) {
            String input = in.getNextPlayerInput();
            Optional<Command.ParsedCommand> result = combatCmdProcessor.parseCommand(g, input);

            if (result.isPresent()) {
                Command.ParsedCommand cmd = result.get();
                String keyword = cmd.cmd().getKeyword().toLowerCase();

                // execute hand and quit immediately
                if (keyword.equals("hand") || keyword.equals("quit")) {
                    g.setActivePlayer(g.getPlayer()); // make sure hand only prints the hand of the player
                    cmd.execute(g);
                    if (!g.isRunning()) {
                        return null;
                    }
                    continue;
                }
                FigureAction action = new FigureAction(g.getPlayer(), cmd);
                if (validateAction(action)) {
                    return action;
                }
            }
        }

        return null;
    }


    /**
     * Checks the end of Game condition and ends game if condition is true.
     */
    public void checkGameEnd() {

        if (g.getPlayer().getStatManager().getCurrentHP() <= 0) {
            printLastBoardState();
            System.out.println(g.getAi().getName() + " wins!");
            g.endMatch();
        } else if (g.getAi().getStatManager().getCurrentHP() <= 0) {
            printLastBoardState();
            System.out.println(g.getPlayer().getName() + " wins!");
            g.endMatch();

        }

    }

    private void printLastBoardState() {
        g.getPlayer().updatePhase(FigurePhase.GAME_OVER);
        g.getAi().updatePhase(FigurePhase.GAME_OVER);
        g.printBoard();
    }

    /**
     * keeps record of the registered Action of a Figure.
     *
     * @param figure is taking action
     * @param cmd    is the selected action
     */
    public record FigureAction(Figure figure, Command.ParsedCommand cmd) {
    }
}
