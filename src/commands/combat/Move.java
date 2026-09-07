package commands.combat;

import commands.core.Argument;
import commands.core.Command;
import figure.Figure;
import figure.FigurePhase;
import figure.poisition.HorizontalPosition;
import figure.poisition.VerticalPosition;
import game.GameState;

/**
 * Handles validation and execution of figure movement when provided by a movement flag.
 *
 * @author ulpv
 */
public class Move extends Command {

    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     */
    public Move() {
        super("move");
        schema.add(new Argument("direction", String.class, true));
    }

    /**
     * Validates the arguments and executes the movement command.
     *
     * @param g    holds the current state of the game
     * @param args contains the movement flags that correspond to the movement direction
     * @return true if command was successful and action is consumed
     */
    @Override
    protected boolean execute(GameState g, String[] args) {


        Figure atTurn = g.getPlayerAtTurn();

        char[] flags = args[0].toCharArray();
        MovementDirection direction = calcFigureDirection(flags);

        if (direction == null) {
            IO.println("ERROR: Invalid movement input!");
            return false;
        }

        Figure opponent = g.getPassiveFigure();
        HorizontalPosition hDir = direction.hPos();
        VerticalPosition vDir = direction.vPos();


        // at least one direction must be specified
        if (hDir == null && vDir == null) {
            return false;
        }

        // check whether figure can move horizontally if it wants to move
        if (hDir != null && canNotMoveHorizontal(atTurn.getHorizontalPosition(), opponent.getHorizontalPosition(), hDir)) {
            return false;
        }


        // check that figure can move vertically if it wants to move
        if (vDir != null && atTurn.getVerticalPosition() == vDir) {
            return false;
        }

        // apply movement
        if (hDir != null) {
            applyHorizontalMove(atTurn, opponent, hDir);
        }

        if (vDir != null) {
            atTurn.setVerticalPosition(vDir);
        }

        return true;
    }

    @Override
    public boolean preValidate(GameState g, String[] args) {
        if (!super.validate(args)) {
            return false;
        }

        Figure atTurn = g.getPlayerAtTurn();

        if (atTurn.getPhase() == FigurePhase.ATTACK) {
            return false;
        }


        char[] flags = args[0].toCharArray();
        MovementDirection direction = calcFigureDirection(flags);

        if (direction == null) {
            IO.println("ERROR: Invalid movement input!");
            return false;
        }

        Figure opponent = g.getPassiveFigure();
        HorizontalPosition hDir = direction.hPos();
        VerticalPosition vDir = direction.vPos();


        // at least one direction must be specified
        if (hDir == null && vDir == null) {
            return false;
        }

        // check whether figure can move horizontally if it wants to move
        if (hDir != null && canNotMoveHorizontal(atTurn.getHorizontalPosition(), opponent.getHorizontalPosition(), hDir)) {
            return false;
        }


        // check that figure can move vertically if it wants to move
        return vDir == null || atTurn.getVerticalPosition() != vDir;
    }

    /**
     * Checks whether there the specified horizontal direction is a valid move.
     *
     * @param atTurn   the figure that chose the action
     * @param opponent the opposing figure that is currently passive
     * @param hDir     the movement direction of the figure at turn
     * @return true if the specified horizontal movement is a valid move
     */
    private boolean canNotMoveHorizontal(HorizontalPosition atTurn, HorizontalPosition opponent, HorizontalPosition hDir) {
        int distance = HorizontalPosition.calculateDistance(atTurn, opponent);

        if (hDir == HorizontalPosition.FORWARD) {
            return distance < 1;
        }

        if (hDir == HorizontalPosition.RETREATED) {
            return distance >= 2;
        }

        return true;
    }


    /**
     * Moves the player horizontally or pushes the opponent if the player is already fronted.
     *
     * @param atTurn   the figure that chose the action
     * @param opponent the opposing figure that is currently passive
     * @param hDir     the movement direction of the figure at turn
     */
    private void applyHorizontalMove(Figure atTurn, Figure opponent, HorizontalPosition hDir) {
        boolean pushOpponent = hDir == HorizontalPosition.FORWARD && atTurn.getHorizontalPosition() == hDir;
        if (pushOpponent) {
            opponent.setHorizontalPosition(hDir);
        } else {
            atTurn.setHorizontalPosition(hDir);
        }
    }

    /**
     * Translates the movement flags provided by the user to a set of movements.
     *
     * @param flags that correspond to the movement directions
     * @return a move direction container
     */
    private MovementDirection calcFigureDirection(char[] flags) {
        if (flags.length == 1) {
            HorizontalPosition h = HorizontalPosition.fromChar(flags[0]);
            VerticalPosition v = VerticalPosition.fromChar(flags[0]);
            return (h != null || v != null) ? new MovementDirection(h, v) : null;
        }

        if (flags.length == 2) {
            HorizontalPosition h = HorizontalPosition.fromChar(flags[0]);
            VerticalPosition v = VerticalPosition.fromChar(flags[1]);
            return (h != null && v != null) ? new MovementDirection(h, v) : null;
        }

        return null;
    }

    /**
     * Container of horizontal and vertical movement direction
     *
     * @param hPos is the direction of the horizontal movement
     * @param vPos is the direction of the vertical movement
     */
    private record MovementDirection(HorizontalPosition hPos, VerticalPosition vPos) {
    }
}

