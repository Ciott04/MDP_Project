package it.unicam.cs.mpgc.rpg125671.model;

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

    public boolean tryEmergencyHeal() {
        if (!hasHealed && getCurrentHp() <= getMaxHp() * HEAL_THRESHOLD) {
            heal((int) (getMaxHp() * HEAL_PERCENTAGE));
            hasHealed = true;
            return true;
        }
        return false;
    }
}
