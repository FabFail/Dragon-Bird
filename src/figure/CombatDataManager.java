package figure;

/**
 * Manages all combat related information of a figure.
 * @author ulprv
 */
public class CombatDataManager {
    private boolean isDodging = false;
    private boolean isBlocking = false;
    private boolean isBaseDefenseActive = false;
    private boolean isGeneralDefenseActive = false;
    private int damageResistance = 0; // Type Damage

    /**
     * start blocking.
     */
    public void startBlocking() {
        this.isBlocking = true;
    }

    /**
     * .
     * @return true if player is blocking
     */
    public boolean isBlocking() {
        return isBlocking;
    }

    /**
     * .
     */
    public void startDodging() {
        this.isDodging = true;
    }

    /**
     * .
     * @return true if player is dodging
     */
    public boolean isDodging() {
        return this.isDodging;
    }

    /**
     * .
     */
    public void startBaseDefense() {
        this.isBaseDefenseActive = true;
    }

    /**
     * .
     * @return true if base defense is active
     */
    public boolean isBaseDefenseActive() {
        return this.isBaseDefenseActive;
    }

    /**
     * starts general defense.
     */
    public void startGeneralDefense() {
        this.isGeneralDefenseActive = true;
    }

    /**
     * .
     * @return true if general defense is active
     */
    public boolean isGeneralDefenseActive() {
        return this.isGeneralDefenseActive;
    }

    /**
     * .
     * @param value of damage resistance
     */
    public void setDamageResistance(int value) {
        this.damageResistance = value;
    }

    /**
     * .
     * @return strength if damage resistance
     */
    public int getDamageResistance() {
        return this.damageResistance;
    }

    /**
     * resets the card effects.
     */
    public void resetCardEffects() {
        this.isDodging = false;
        this.isBlocking = false;
        this.isBaseDefenseActive = false;
        this.isGeneralDefenseActive = false;
        this.damageResistance = 0;
    }
}
