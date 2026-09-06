package game;


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
import game.enemyai.EnemyAI;

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

    private final EnemyAI ai;


    /**
     * Creates the Game with all its subsystems.
     * @param seed that determines random behavior.
     */
    public Game(int seed) {
        Random rnd = new Random(seed);
        this.g = new GameState(rnd);
        this.in = new InputManager();
        this.ai = new EnemyAI();
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
            runSetupLoop();

            if (this.g.isRunning()) {
                runCombatLoop();
            }

        } finally {
            this.in.close();
        }
    }

    /**
     * Handles all actions before the main game loop is starting
     */
    private void runSetupLoop() {
        IO.println("Use one of the following commands: create fighter, "
                + "create card, "
                + "list fighter, "
                + "list card, "
                + "set avatar, "
                + "set deck, "
                + "list deck, "
                + "start, "
                + "quit");

        while (this.g.getGamePhase() == GamePhase.SETUP && g.isRunning()) {
            String input = this.in.getNextPlayerInput();

            setupCmdProcessor.processCommand(g, input);
        }
    }

    /**
     * Handles sequence of combat's phase game loop
     */
    private void runCombatLoop() {
        while (g.isRunning()) {
            g.nextTurn();

            boolean playerIsFaster = g.getPlayer().getStatManager().getSpeed() > g.getAi().getStatManager().getSpeed();
            Figure fastFigure = playerIsFaster ? g.getPlayer() : g.getAi();
            Figure slowFigure = playerIsFaster ? g.getAi() : g.getPlayer();

            // first half phase
            turnSequence(fastFigure, slowFigure);
            if (!g.isRunning()) {
                break;
            }

            // second half phase
            turnSequence(slowFigure, fastFigure);
            if (!g.isRunning()) {
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

        g.setActivePlayer(attacker);
        executeFigureAction(attacker, FigurePhase.ATTACK);
        checkGameEnd();

        g.setActivePlayer(defender);
        executeFigureAction(defender, FigurePhase.DEFENSE);
        defender.endDefensePhase();
        checkGameEnd();
    }

    /**
     * Decides whether the current execution of the action is done via player or ai logic
     *
     * @param actor is the figure that is currently doing an action
     * @param phase describes whether an attack or defense action is required
     */
    private void executeFigureAction(Figure actor, FigurePhase phase) {
        if (actor == g.getAi()) {
            executeAITurn();
        } else {
            executePlayerTurn(phase);
        }
        actor.tick();
    }

    /**
     * Get the player input and processes it and executes the command
     *
     * @param phase is the current phase of the figure that decides upon the action that is required
     */
    private void executePlayerTurn(FigurePhase phase) {
        IO.println(String.format("Player chooses %s move", phase.name().toLowerCase()));
        IO.println(String.format("Available Card Cost: %d\n\n", g.getPlayer().getStatManager().getCardCost()));
        boolean actionConsumed = false;

        while (!actionConsumed && g.isRunning()) {
            String input = in.getNextPlayerInput();
            actionConsumed = combatCmdProcessor.processCommand(g, input);
        }
    }

    /**
     * Get the ai input and processes it and executes the command
     *
     */
    private void executeAITurn() {
        String enemyInput = ai.getNextAction(g);
        combatCmdProcessor.processCommand(g, enemyInput);
    }

    /**
     * Checks the end of Game condition and ends game if condition is true.
     */
    public void checkGameEnd() {
        if (g.getPlayer().getStatManager().getCurrentHP() <= 0 || g.getAi().getStatManager().getCurrentHP() <= 0) {
            g.endGame();
        }
    }
}
