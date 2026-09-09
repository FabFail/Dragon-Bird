package commands.combat;

import commands.core.Command;
import figure.Figure;
import figure.FigurePhase;
import game.GameState;
import game.InputManager;

import java.util.Random;

/**
 * handles validation and execution of a base attack.
 *
 * @author ulprv
 */
public class Smack extends Command {
    private final InputManager inputManager;
    private int attackPower;

    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     *
     * @param in is the input manager
     */
    public Smack(InputManager in) {
        super("smack");
        this.inputManager = in;
    }

    @Override
    protected boolean execute(GameState g, String[] args) {

        Figure attacker = g.getPlayerAtTurn();
        Figure defender = g.getPassiveFigure();


        if (attacker == g.getAi()) {
            this.attackPower = g.getAi().getStatManager().isPowerNapActive() ? 4 : 3;
        }

        if (defender.getCombatDataManager().isBaseDefenseActive()) {
            return true;
        }

        defender.getStatManager().takeDamage(attackPower);
        System.out.println(defender.getName() + " takes " + this.attackPower + " damage!");
        attacker.getStatManager().updateCardCost(attackPower);

        return true;
    }

    /**
     * plays minigame to decide over damage dealt.
     *
     * @param actor the figure doing the action
     * @param rnd object to draw whole numbers
     */
    public void playMinigame(Figure actor, Random rnd) {
        System.out.println("Smack Math Challenge!");

        int bonusAttackPower = 0;

        int partialSsum = 0;

        int sequenceLength = actor.getStatManager().isPowerNapActive() ? 4 : 3;
        int[] rnds = new int[sequenceLength];

        for (int i = 0; i < sequenceLength; i++) {
            rnds[i] = rnd.nextInt(1, 100);
        }

        for (int i = 0; i < rnds.length; i++) {
            if (i > 0) {
                System.out.print(",");
            }
            System.out.print(rnds[i]);
        }
        System.out.println();


        int j = 0;
        boolean failed = false;
        while (j < sequenceLength && !failed) {
            partialSsum += rnds[j];
            String playerResponse = inputManager.getNextPlayerInput();
            int guess;
            try {
                guess = Integer.parseInt(playerResponse);
                if (guess == partialSsum) {
                    bonusAttackPower++;
                } else {
                    failed = true;
                }
            } catch (NumberFormatException e) {
                failed = true;
            }
            j++;
        }
        if (failed) {
            System.out.println("Oh no!");
        } else {
            System.out.println("Correct!");
        }

        this.attackPower = bonusAttackPower;
    }

    @Override
    public boolean preValidate(GameState g, String[] args) {
        if (!super.validate(args)) {
            return false;
        }
        return g.getPlayerAtTurn().getPhase() == FigurePhase.ATTACK;
    }
}
