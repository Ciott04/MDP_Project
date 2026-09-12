package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Boss finale del dungeon. Estende {@link Monster} con la capacità di curarsi
 * una volta sola quando gli HP scendono sotto il 30% del massimo.
 */
public class Boss extends Monster implements Healable {

    private static final double HEAL_THRESHOLD = 0.3;
    private static final double HEAL_PERCENTAGE = 0.2;

    private boolean hasHealed;

    public Boss(String name, int maxHp, int attack, int defense, int speed, int expReward) {
        super(name, maxHp, attack, defense, speed, expReward);
        this.hasHealed = false;
    }

    @Override
    public void heal(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("La cura deve essere maggiore di 0.");
        restoreHp(amount);
    }

    /**
     * Tenta la cura d'emergenza: se il boss non si è ancora curato e i suoi HP
     * sono scesi sotto il {@code 30%} del massimo, si cura del {@code 20%} degli HP massimi.
     *
     * @return {@code true} se la cura è avvenuta, {@code false} altrimenti.
     */
    public boolean tryEmergencyHeal() {
        if (!hasHealed && getCurrentHp() <= getMaxHp() * HEAL_THRESHOLD) {
            heal((int) (getMaxHp() * HEAL_PERCENTAGE));
            hasHealed = true;
            return true;
        }
        return false;
    }
}
