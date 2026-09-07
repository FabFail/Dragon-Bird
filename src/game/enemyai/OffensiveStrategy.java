package game.enemyai;

import cards.Card;
import cards.CardType;
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
public class OffensiveStrategy implements Strategy {

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

        result = moveForwardPolicy(g);
        if (result != null) {
            return result;
        }

        result = useOffensiveEffectCardPolicy(g);
        if (result != null) {
            return result;
        }

        return Actions.CHILL.getCommandString();
    }

    private String getOffenseAction(GameState g) {
        String result;
        result = useOffenseCardPolicy(g);
        if (result != null) {
            return result;
        }

        result = usePowerNapPolicy(g);
        if (result != null) {
            return result;
        }

        result = useAnotherEffectCardPolicy(g);
        if (result != null) {
            return result;
        }

        result = useAnyCardPolicy(g);
        if (result != null) {
            return result;
        }

        return Actions.SMACK.getCommandString();
    }

    private String useOffenseCardPolicy(GameState g) {
        Figure ai = g.getAi();
        Card strongestCombatCard = getStrongestCombatCard(g.getAi().getCardManager().getHand(),
                ai.getStatManager().getCardCost());

        if (ai.getStatManager().isPowerNapActive() && strongestCombatCard != null) {
            g.changeAIToDef(true);
            return strongestCombatCard.getName();
        }

        return null;
    }

    private String usePowerNapPolicy(GameState g) {
        Figure ai = g.getAi();
        int maxMissingCardCost = 6;

        if (ai.getStatManager().isPowerNapActive()) {
            return null;
        }

        Card useableCard = g.getAi().getCardManager().getHand().stream()
                .filter(c -> c.getType().isCombatCard())
                .filter(card -> card.getValue() >= 15)
                .filter(card -> card.getCost() <= ai.getStatManager().getCardCost()
                        + maxMissingCardCost)
                .findFirst()
                .orElse(null);

        if (useableCard == null) {
            return null;
        }
        return Actions.POWER_NAP.getCommandString();
    }

    private String useAnotherEffectCardPolicy(GameState g) {
        Figure ai = g.getAi();
        int minLeftOverCardCost = 5;

        if (ai.getStatManager().getCardCost() < 20) {
            return null;
        }

        Card useableCard = g.getAi().getCardManager().getHand().stream()
                .filter(c -> c.getType().isEffectCard())
                .filter(card -> card.getCost() <= ai.getStatManager().getCardCost() - minLeftOverCardCost)
                .findFirst()
                .orElse(null);
        if (useableCard != null) {
            return useableCard.getName();
        }

        return null;
    }

    private String useAnyCardPolicy(GameState g) {
        Figure ai = g.getAi();

        if (ai.getCardManager().getHand().size() != 6) {
            return null;
        }

        Card useAbleCard = ai.getCardManager().getHand().stream()
                .filter(card -> card.getCost() <= ai.getStatManager().getCardCost())
                .findFirst()
                .orElse(null);
        if (useAbleCard != null) {
            return useAbleCard.getName();
        }

        return null;
    }


    private String moveForwardPolicy(GameState g) {
        Figure ai = g.getAi();
        Figure player = g.getPlayerAtTurn();

        boolean hasCombatCard = ai.getCardManager().getHand().stream()
                .anyMatch(c -> c.getType().isCombatCard());

        boolean canMoveForward = HorizontalPosition
                .calculateDistance(ai.getHorizontalPosition(), player.getHorizontalPosition()) > 0;

        if (g.getAi().getStatManager().isPowerNapActive() && hasCombatCard && canMoveForward) {
            return Actions.MOVE_FORWARD.getCommandString();
        }

        return null;
    }

    private String useOffensiveEffectCardPolicy(GameState g) {
        Figure ai = g.getAi();
        Card strongestCombatCard = getStrongestCombatCard(ai.getCardManager().getHand(),
                ai.getStatManager().getCardCost());
        if (strongestCombatCard == null) {
            return null;
        }

        if (strongestCombatCard.getType() == CardType.MELEE) {
            Card c = getUsablePowerCards(ai.getCardManager().getHand(), ai.getStatManager().getCardCost());
            if (c != null) {
                return c.getName();
            }
        } else if (strongestCombatCard.getType() == CardType.DISTANCE) {
            Card c = getUsableEnergyCards(ai.getCardManager().getHand(), ai.getStatManager().getCardCost());
            if (c != null) {
                return c.getName();
            }
        }

        return null;
    }


    private Card getUsablePowerCards(List<Card> hand, int availableCardCost) {
        return hand.stream()
                .filter(c -> c.getType() == CardType.POWER_PLUS)
                .filter(card -> card.getCost() <= availableCardCost)
                .findFirst()
                .orElse(null);
    }

    private Card getUsableEnergyCards(List<Card> hand, int availableCardCost) {
        return hand.stream()
                .filter(c -> c.getType() == CardType.ENERGY_PLUS)
                .filter(card -> card.getCost() <= availableCardCost)
                .findFirst()
                .orElse(null);
    }

    private Card getStrongestCombatCard(List<Card> hand, int availableCardCost) {
        List<Card> usableCombatCards = hand.stream()
                .filter(c -> c.getType().isCombatCard())
                .filter(card -> card.getCost() <= availableCardCost)
                .toList();

        int maxValue = usableCombatCards.stream().mapToInt(Card::getValue).max().orElse(-1);

        if (maxValue == -1) {
            return null;
        }

        return usableCombatCards.stream().filter(c -> c.getValue() == maxValue).findFirst().orElse(null);
    }

}
