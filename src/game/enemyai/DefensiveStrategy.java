package game.enemyai;

import cards.Card;
import figure.Figure;
import figure.FigurePhase;
import figure.poisition.HorizontalPosition;
import game.GameState;

import java.util.List;

/**
 * Contains AI logic of a defensive strategy with policies for attack and defensive actions.
 *
 * @author ulprv
 */
public class DefensiveStrategy implements Strategy {
    @Override
    public String getNextAction(GameState g) {
        if (g.getAi().getPhase() == FigurePhase.DEFENSE) {
            return getDefenseAction(g);
        } else {
            return getOffenseAction(g);
        }
    }

    private String getDefenseAction(GameState g) {
        String result;

        result = useDefCardPolicy(g);
        if (result != null) {
            return result;
        }

        result = retreatPolicy(g);
        if (result != null) {
            return result;
        }

        result = useEffectCardPolicy(g);
        if (result != null) {
            return result;
        }

        return Actions.CHILL.getCommandString();
    }

    private String getOffenseAction(GameState g) {
        String result = useOffensiveEffectCardPolicy(g);
        if (result != null) {
            return result;
        }
        return Actions.SMACK.getCommandString();
    }

    private String useOffensiveEffectCardPolicy(GameState g) {
        List<Card> hand = g.getAi().getCardManager().getHand();
        int availableCardCost = g.getAi().getStatManager().getCardCost();
        int minLeftOverCardCost = 5;
        int minAvailableCardCost = 20;

        if (availableCardCost < minAvailableCardCost) {
            return null;
        }

        List<Card> usableEffectCards = hand.stream()
                .filter(c -> c.getType().isEffectCard())
                .filter(card -> availableCardCost - card.getCost() > minLeftOverCardCost)
                .toList();

        return usableEffectCards.getFirst().getName();
    }


    private String useDefCardPolicy(GameState g) {
        List<Card> hand = g.getAi().getCardManager().getHand();
        int availableCardCost = g.getAi().getStatManager().getCardCost();
        boolean isPowerNapActionFour = g.getAi().getStatManager().getRemainingPowerNapActions() == 1;
        int turnNumber = g.getTurnNumber();

        if (turnNumber < 5) {
            return null;
        }

        if (!isPowerNapActionFour) {
            return null;
        }

        List<Card> usableDefenseCards = hand.stream()
                .filter(c -> c.getType().isDefenseCard())
                .filter(card -> card.getCost() <= availableCardCost)
                .toList();

        if (usableDefenseCards.isEmpty()) {
            return null;
        }

        return usableDefenseCards.getFirst().getName();
    }

    private String retreatPolicy(GameState g) {
        if (g.getAi().getHorizontalPosition() == HorizontalPosition.FORWARD) {
            return Actions.MOVE_RETREAT.getCommandString();
        }

        return null;
    }

    private String useEffectCardPolicy(GameState g) {
        Figure ai = g.getAi();
        List<Card> hand = ai.getCardManager().getHand();
        int availableCardCost = g.getAi().getStatManager().getCardCost();
        int minLeftOverCardCost = 5;


        if (availableCardCost < 20) {

            return null;
        }

        List<Card> usableEffectCards = hand.stream()
                .filter(c -> c.getType().isEffectCard())
                .filter(card -> availableCardCost - card.getCost() > minLeftOverCardCost)
                .toList();

        if (usableEffectCards.isEmpty()) {
            return null;
        }

        return usableEffectCards.getFirst().getName();
    }
}
