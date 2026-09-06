package commands.combat;

import commands.core.Command;
import figure.Figure;
import figure.FigurePhase;
import game.GameState;
import game.InputManager;

/**
 * handles validation and execution of a base attack.
 *
 * @author ulprv
 */
public class Smack extends Command {
    private final InputManager inputManager;

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
        if (!super.validate(args)) {
            return false;
        }

        Figure attacker = g.getPlayerAtTurn();
        Figure defender = g.getPassiveFigure();

        if (attacker.getPhase() == FigurePhase.DEFENSE) {
            return false;
        }

        int bonusAttackPower;
        if (attacker == g.getPlayer()) {
            bonusAttackPower = playMinigame(g);
        } else {
            bonusAttackPower = 4;
        }

        if (defender.getCombatDataManager().isBaseDefenseActive()) {
            return true;
        }

        defender.getStatManager().takeDamage(bonusAttackPower);
        attacker.getStatManager().updateCardCost(bonusAttackPower);

        return true;
    }

    private int playMinigame(GameState g) {
        IO.println("Smack Math Challenge!");

        int bonusAttackPower = 0;

        int partialSsum = 0;

        int sequenceLength = g.getPlayerAtTurn().getStatManager().isPowerNapActive() ? 4 : 3;
        int[] rnds = new int[sequenceLength];

        for (int i = 0; i < sequenceLength; i++) {
            rnds[i] = g.drawWholeNumber();
        }

        for (int i = 0; i < rnds.length; i++) {
            if (i > 0) {
                IO.print(",");
            }
            IO.print(rnds[i]);
        }
        IO.println();


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
            IO.println("Oh no!");
        } else {
            IO.println("Correct");
        }

        return bonusAttackPower;
    }
}
