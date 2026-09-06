package figure;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 * @author ulprv
 */
public class StatManager {
    private FigureData fd;

    private Buff powerNap;
    private int actionsSincePowerNap = 0;
    private final List<Buff> buffs;
    private int currentHP;
    private int cardCost;

    /**
     * creates the stat manager module of a figure.
     * @param fd figure data
     */
    public StatManager(FigureData fd) {
        this.fd = fd;
        this.buffs = new ArrayList<>();

        this.currentHP = this.fd.health();
        this.cardCost = 10;
    }

    /**
     * .
     * @param fd figure data
     */
    public void setData(FigureData fd) {
        this.fd = fd;
        this.currentHP = this.fd.health();
    }

    /**
     * .
     */
    public void startPowerNap() {
        this.powerNap = new Buff(
                4,
                4,
                0,
                0,
                5);

        actionsSincePowerNap = -1; // as the first tick comes right after powerNap
    }


    /**
     * .
     * @return power nap turn count
     */
    public int getPowerNapTurnCount() {
        if (this.powerNap == null) {
            return 0;
        }

        return this.powerNap.getTurnsRemaining();
    }

    /**
     * .
     * @param buff strengthen a stat
     */
    public void applyBuff(Buff buff) {
        this.buffs.add(buff);
    }

    /**
     * get speed value.
     * @return speed
     */
    public int getSpeed() {
        return this.fd.speed() + buffs.stream().mapToInt(Buff::getSpeedBonus).sum();
    }

    /**
     * get power value.
     * @return power
     */
    public int getPower() {
        int powerNapBonus = (powerNap == null || powerNap.getTurnsRemaining() < 0) ? 0 : powerNap.getPowerBonus();

        return this.fd.power() + powerNapBonus + buffs.stream().mapToInt(Buff::getPowerBonus).sum();
    }

    /**
     * .
     * @return energy
     */
    public int getEnergy() {
        int powerNapBonus = (powerNap == null || powerNap.getTurnsRemaining() < 0) ? 0 : powerNap.getEnergyBonus();
        return this.fd.energy()
                + powerNapBonus
                + buffs.stream().mapToInt(Buff::getEnergyBonus).sum();
    }

    /**
     * .
     * @return defense
     */
    public int getDefense() {
        return this.fd.defense() + buffs.stream().mapToInt(Buff::getDefenseBonus).sum();
    }

    /**
     * .
     * @return current hp
     */
    public int getCurrentHP() {
        return this.currentHP;
    }


    /**
     * updates buffs after each action.
     */
    public void tick() {
        actionsSincePowerNap++;
        if (this.powerNap != null) {
            this.powerNap.tick();

            if (this.powerNap.getTurnsRemaining() == 0) {
                this.powerNap = null;
            }
        }
    }

    /**
     * reduces current hp.
     * @param amount is taken from current hp.
     */
    public void takeDamage(int amount) {
        this.currentHP = Math.clamp(currentHP - amount, 0, fd.health());

    }

    /**
     * increases current hp, but cannot exceed max hp.
     * @param amount is added to current hp.
     */
    public void regainHealth(int amount) {
        if (currentHP > 0) {
            this.currentHP = Math.clamp(currentHP + amount, 0, fd.health());
        }
    }

    /**
     * checks whether a power nap is active.
     * @return true if active
     */
    public boolean isPowerNapActive() {
        return (this.powerNap != null && this.powerNap.getTurnsRemaining() > 0);

    }

    /**
     * updates current card cost resource.
     * @param amount is added or subtracted from card cost
     */
    public void updateCardCost(int amount) {
        this.cardCost = Math.max(0, this.cardCost + amount);
    }

    /**
     * .
     * @return card cost resource
     */
    public int getCardCost() {
        return this.cardCost;
    }

    /**
     * provides information about figure data stats.
     * @return stats
     */
    public FigureData getFigureData() {
        return this.fd;
    }
}
