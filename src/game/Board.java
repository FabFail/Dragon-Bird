package game;

import figure.Figure;
import figure.poisition.HorizontalPosition;
import figure.poisition.VerticalPosition;

/**
 * Renders the board.
 *
 * @author ulprv
 */
public class Board {
    private static final String BLANK = " ";

    /**
     * Renders board on call.
     * @param player is the figure of the player
     * @param ai is the figure of the AI
     */
    public void printBoard(Figure player, Figure ai) {
        //line one
        String line1 = firstLine(player, ai);
        String line2 = secondLine(player, ai);
        String line3 = thirdLine(player, ai);
        String line4 = fourthLine(player, ai);
        String line5 = fifthLine(player, ai);
        String line6 = sixthLine(player, ai);
        IO.println(line1);
        IO.println(line2);
        IO.println(line3);
        IO.println(line4);
        IO.println(line5);
        IO.println(line6);
        IO.println("#".repeat(24));
        // 24 Symbols per line
        // 12 per figure
    }

    private static String firstLine(Figure player, Figure ai) {
        // left justify/ minimum of 10/ maximum of 10/ String var/ vs/...
        return String.format(
                "%-10.10s vs %10.10s",
                player.getName(),
                ai.getName()
        );
    }

    private static String secondLine(Figure player, Figure ai) {
        float playerHP = player.getHealthPercentage();
        float aiHP = ai.getHealthPercentage();

        String playerBars = healthBars(playerHP);
        String aiBars = new StringBuilder(healthBars(aiHP)).reverse().toString();

        String playerCombatMode = player.getPhase().getSymbol();
        String aiCombatMode = ai.getPhase().getSymbol();

        return String.format("%s%s  %s%s", playerBars, playerCombatMode, aiCombatMode, aiBars);
    }

    private static String healthBars(float healthPercentage) {
        int barCount = Math.clamp((int) (healthPercentage * 10), 0, 10);

        return "|".repeat(barCount) + "_".repeat(10 - barCount);
    }


    private static String thirdLine(Figure player, Figure ai) {
        String playerNap = (player.getVerticalPosition() == VerticalPosition.ASCENDED)
                ? (player.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(3) + napIndicator(player) + BLANK.repeat(6)
                : BLANK.repeat(8) + napIndicator(player) + BLANK.repeat(1))
                : BLANK.repeat(10);
        String aiNap = (ai.getVerticalPosition() == VerticalPosition.ASCENDED)
                ? (ai.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(6) + napIndicator(ai) + BLANK.repeat(3)
                : BLANK.repeat(1) + napIndicator(ai) + BLANK.repeat(8))
                : BLANK.repeat(10);

        return String.format("%s    %s", playerNap, aiNap);
    }

    private static String napIndicator(Figure fig) {
        if (fig.getStatManager().getPowerNapTurnCount() > 1) {
            return ":";
        } else if (fig.getStatManager().getPowerNapTurnCount() == 1) {
            return ".";
        }
        return " ";
    }

    private static String fourthLine(Figure player, Figure ai) {
        String playerAvatar = (player.getVerticalPosition() == VerticalPosition.ASCENDED)
                ? (player.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(2) + player.getAvatar() + BLANK.repeat(7)
                : BLANK.repeat(7) + player.getAvatar() + BLANK.repeat(2))
                : BLANK.repeat(10);
        String aiAvatar = (ai.getVerticalPosition() == VerticalPosition.ASCENDED)
                ? (ai.getHorizontalPosition() == HorizontalPosition.FORWARD
                ? BLANK.repeat(2) + ai.getAvatar() + BLANK.repeat(7)
                : BLANK.repeat(7) + ai.getAvatar() + BLANK.repeat(2))
                : BLANK.repeat(10);

        return String.format("%s    %s", playerAvatar, aiAvatar);
    }

    private static String fifthLine(Figure player, Figure ai) {
        String playerNap = (player.getVerticalPosition() == VerticalPosition.GROUNDED)
                ? (player.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(3) + napIndicator(player) + BLANK.repeat(6)
                : BLANK.repeat(8) + napIndicator(player) + BLANK.repeat(1))
                : BLANK.repeat(10);
        String aiNap = (ai.getVerticalPosition() == VerticalPosition.GROUNDED)
                ? (ai.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(6) + napIndicator(ai) + BLANK.repeat(3)
                : BLANK.repeat(1) + napIndicator(ai) + BLANK.repeat(8))
                : BLANK.repeat(10);

        return String.format("%s    %s", playerNap, aiNap);
    }

    private static String sixthLine(Figure player, Figure ai) {
        String playerAvatar = (player.getVerticalPosition() == VerticalPosition.GROUNDED)
                ? (player.getHorizontalPosition() == HorizontalPosition.RETREATED
                ? BLANK.repeat(2) + player.getAvatar() + BLANK.repeat(7)
                : BLANK.repeat(7) + player.getAvatar() + BLANK.repeat(2))
                : BLANK.repeat(10);
        String aiAvatar = (ai.getVerticalPosition() == VerticalPosition.GROUNDED)
                ? (ai.getHorizontalPosition() == HorizontalPosition.FORWARD
                ? BLANK.repeat(2) + ai.getAvatar() + BLANK.repeat(7)
                : BLANK.repeat(7) + ai.getAvatar() + BLANK.repeat(2))
                : BLANK.repeat(10);


        return String.format("%s    %s", playerAvatar, aiAvatar);
    }
}
