package it.unicam.cs.mpgc.rpg125671.model;

public abstract class GameCharacter implements Combatant {

    private final String name;

    private int maxHp;
    private int currentHp;

    private int attack;

    private int defense;

    private int speed;

    protected GameCharacter(String name, int maxHp, int attack, int defense, int speed) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        if (maxHp <= 0)
            throw new IllegalArgumentException("I punti vita massimi devono essere maggiori di 0.");
        if (attack <= 0 || defense <= 0 || speed <= 0)
            throw new IllegalArgumentException("Gli attributi non possono essere negativi.");

        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    @Override
    public void takeDamage(int finalDamage) {
        if (finalDamage <= 0)
            throw new IllegalArgumentException("Il danno deve essere maggiore di 0.");
        this.currentHp = Math.max(0, this.currentHp - finalDamage);
    }

    @Override
    public boolean isAlive() {
        return this.currentHp > 0;
    }

    protected void restoreHp(int amount) {
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }

    protected void increaseMaxHp(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento della vita deve essere maggiore di 0.");
        this.maxHp += amount;
    }

    protected void increaseAttack(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento dell'attacco deve essere maggiore di 0.");
        this.attack += amount;
    }

    protected void increaseDefense(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento della difesa deve essere maggiore di 0.");
        this.defense += amount;
    }

    protected void increaseSpeed(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento della velocità deve essere maggiore di 0.");
        this.speed += amount;
    }

    // --- GETTER ---

    @Override
    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getCurrentHp() {
        return currentHp;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSpeed() {
        return speed;
    }
}
