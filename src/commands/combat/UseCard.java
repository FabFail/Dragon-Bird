package commands.combat;

import cards.Card;
import cards.CardType;
import commands.core.Command;
import figure.Figure;
import figure.FigurePhase;
import figure.poisition.HorizontalPosition;
import figure.poisition.VerticalPosition;
import game.GameState;
import figure.Buff;

/**
 * handles validation and execution when using a card.
 *
 * @author ulprv
 */
public class UseCard extends Command {
    private final Card card;

    /**
     * Creates the combat command and sets the keyword and optionally a schema.
     *
     * @param card that is getting used
     */
    public UseCard(Card card) {
        super(card.getName());
        this.card = card;
    }

    @Override
    public boolean execute(GameState g, String[] args) {
        Figure activePlayer = g.getPlayerAtTurn();
        activePlayer.getCardManager().getHand().remove(card);


        if (!checkAccuracy(g)) {
            return true;
        }

        CardType type = card.getType();

        switch (type) {
            case CardType.DAMAGE -> {
                return executeDamageCard(g);
            }
            case CardType.BLOCK -> {
                activePlayer.getCombatDataManager().startBlocking();
                return true;
            }
            case CardType.DISTANCE -> {
                return executeRangeAttack(g);
            }
            case CardType.DODGE -> {
                activePlayer.getCombatDataManager().startDodging();
                return true;
            }
            case CardType.MELEE -> {
                return executeMeleeAttack(g);
            }
            case CardType.REGAIN -> {
                activePlayer.getStatManager().regainHealth(card.getValue());
                return true;
            }
            case CardType.BAS_DEFENSE -> activePlayer.getCombatDataManager().startBaseDefense();
            case CardType.GEN_DEFENSE -> activePlayer.getCombatDataManager().startGeneralDefense();
            case ENERGY_PLUS, ENERGY_MINUS,
                 POWER_PLUS, POWER_MINUS,
                 DEFENSE_PLUS, DEFENSE_MINUS,
                 SPEED_PLUS, SPEED_MINUS -> {
                return applyStatBuff(g);
            }
            default -> {
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean preValidate(GameState g, String[] args) {
        Figure activePlayer = g.getPlayerAtTurn();
        // phase check
        if (!card.getType().isUsableIn(activePlayer.getPhase())) {
            return false;
        }

        // card cost check
        return activePlayer.getStatManager().getCardCost() >= card.getCost();
    }

    private boolean checkAccuracy(GameState g) {
        Figure activePlayer = g.getPlayerAtTurn();
        activePlayer.getStatManager().updateCardCost(-card.getCost());

        if (activePlayer == g.getPlayer() && (card.getType() == CardType.DAMAGE
                || card.getType() == CardType.MELEE
                || card.getType() == CardType.DISTANCE) && card.getValue() >= 15) {
            g.changeAIToOff(true);
        }

        // accuracy check
        int accuracyRoll = g.getAccuracyRoll();
        return accuracyRoll < card.getAccuracy();
    }

    private boolean applyStatBuff(GameState g) {
        boolean positive = card.getType().name().endsWith("PLUS");
        Figure target = positive ? g.getPlayerAtTurn() : g.getPassiveFigure();
        int value = positive ? card.getValue() : -card.getValue();

        int power = 0;
        int energy = 0;
        int defense = 0;
        int speed = 0;

        switch (card.getType()) {
            case POWER_PLUS, POWER_MINUS -> power = value;
            case ENERGY_PLUS, ENERGY_MINUS -> energy = value;
            case DEFENSE_PLUS, DEFENSE_MINUS -> defense = value;
            case SPEED_PLUS, SPEED_MINUS -> speed = value;
            default -> throw new IllegalStateException("Not a stat buff");
        }
        target.getStatManager().applyBuff(new Buff(power, energy, defense, speed, Integer.MAX_VALUE));

        return true;
    }

    private boolean executeDamageCard(GameState g) {
        Figure activePlayer = g.getPlayerAtTurn();
        Figure defender = g.getPassiveFigure();

        if (activePlayer.getPhase() == FigurePhase.DEFENSE) {
            activePlayer.getCombatDataManager().setDamageResistance(card.getValue());
        } else {
            int damage = Math.max(0, card.getValue() - defender.getCombatDataManager().getDamageResistance());
            g.getPassiveFigure().getStatManager().takeDamage(damage);
        }
        return true;
    }


    private boolean executeMeleeAttack(GameState g) {
        Figure activePlayer = g.getPlayerAtTurn();
        Figure defender = g.getPassiveFigure();

        if (!defender.getCombatDataManager().isBlocking()) {
            int damage = calcDamage(activePlayer, defender, false);
            defender.getStatManager().takeDamage(damage);
            IO.println(String.format("%s takes %d damage!", defender.getName(), damage));
        }
        return true;
    }

    private boolean executeRangeAttack(GameState g) {
        Figure activePlayer = g.getPlayerAtTurn();
        Figure defender = g.getPassiveFigure();

        if (!defender.getCombatDataManager().isDodging()) {
            int damage = calcDamage(activePlayer, defender, true);
            defender.getStatManager().takeDamage(damage);
            IO.println(String.format("%s takes %d damage!", defender.getName(), damage));
        }

        return true;
    }

    private int calcDamage(Figure attacker, Figure defender, boolean isDistance) {

        int dmg = card.getValue();
        int bonusAtk = attacker.getHorizontalPosition() == HorizontalPosition.FORWARD ? 8 : 0;

        int bonusDef = 0;
        if (defender.getVerticalPosition() == VerticalPosition.GROUNDED) {
            bonusDef = isDistance ? 4 : 0;
        } else if (defender.getVerticalPosition() == VerticalPosition.ASCENDED) {
            bonusDef = isDistance ? 0 : 4;
        }

        int distanceFactor = 4 - HorizontalPosition.calculateDistance(
                attacker.getHorizontalPosition(),
                defender.getHorizontalPosition());

        int atkStat = isDistance ? attacker.getStatManager().getEnergy() : attacker.getStatManager().getPower();
        int defStat = defender.getStatManager().getDefense();

        double rawDmg = (double) (dmg * (atkStat + bonusAtk) * distanceFactor) / (defStat + bonusDef);
        int finalDamage = (int) Math.round(rawDmg);

        if (defender.getCombatDataManager().isGeneralDefenseActive()) {
            finalDamage = finalDamage / 3;
        }
        finalDamage = finalDamage - defender.getCombatDataManager().getDamageResistance();
        return Math.max(0, finalDamage);
    }
}
