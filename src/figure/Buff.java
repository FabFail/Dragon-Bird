package figure;

/**
 * buffs a figures stats for random amount of turns.
 * @author ulprv
 */
public class Buff {
    private final int powerBonus;
    private final int energyBonus;
    private final int defenseBonus;
    private final int speedBonus;
    private int turnsRemaining;

    /**
     * creates a buff with set values.
     * @param powerBonus buffs the power of a figure
     * @param energyBonus buffs the energy of a figure
     * @param defenseBonus buffs the defense of a figure
     * @param speedBonus buffs the speed of a figure
     * @param turnsRemaining holds information on how many actions a turn lasts
     */
    public Buff(int powerBonus, int energyBonus, int defenseBonus, int speedBonus, int turnsRemaining) {
        this.powerBonus = powerBonus;
        this.energyBonus = energyBonus;
        this.defenseBonus = defenseBonus;
        this.speedBonus = speedBonus;
        this.turnsRemaining = turnsRemaining;
    }

    /**
     * get the amount of actions the buff still lasts.
     * @return amount of actions the buff has left before running out
     */
    public int getTurnsRemaining() {
        return this.turnsRemaining;
    }

    /**
     * reduces the amount of actions until the buff runs out.
     */
    public void tick() {
        this.turnsRemaining--;
    }

    /**
     * gets the strength of the buff.
     * @return power value of buff
     */
    public int getPowerBonus() {
        return  this.powerBonus;
    }

    /**
     * gets the strength of the buff.
     * @return energy value of buff
     */
    public int getEnergyBonus() {
        return this.energyBonus;
    }

    /**
     * gets the strength of the buff.
     * @return defense value of buff
     */
    public int getDefenseBonus() {
        return this.defenseBonus;
    }

    /**
     * gets the strength of the buff.
     * @return speed value of buff
     */
    public int getSpeedBonus() {
        return this.speedBonus;
    }
}
