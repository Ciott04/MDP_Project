package it.unicam.cs.mpgc.rpg125671.Model;

public abstract class Hero implements Combatant, Healable {

    private final String name;

    private int maxHp;
    private int currentHp;

    private int expToNextLevel;
    private int currentExp;
    private int level;

    private int attack;

    private int defense;

    private int speed;

    public Hero(String name, int maxHp, int attack, int defense, int speed) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        if (maxHp <= 0)
            throw new IllegalArgumentException("I punti vita massimi devono essere maggiori di 0.");
        if (attack <= 0 || defense <= 0 || speed <= 0)
            throw new IllegalArgumentException("Gli attributi dell'eroe non possono essere negativi.");

        this.name = name;
        this.maxHp = maxHp;
        currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;

    }

    public void takeDamage(int finalDamage) {
        if (finalDamage <= 0)
            throw new IllegalArgumentException("Il danno non può essere negativo.");
        this.currentHp = Math.max(0, this.currentHp - finalDamage);
    }

    public void heal(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("La cura deve essere maggiore di 0.");
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }

    public boolean isAlive() {
        return this.currentHp > 0;
    }

    public void gainExp(int exp) {
        if (exp <= 0)
            throw new IllegalArgumentException("I punti esperienza non possono essere minori di 1.");
        this.currentExp += exp;
        while (this.currentExp >= this.expToNextLevel) {
            this.currentExp -= this.expToNextLevel;
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        this.expToNextLevel = (int) (this.expToNextLevel * 1.5);
        onLevelUp();
    }

    /**
     * Metodo hook chiamato al passaggio di livello da ogni classe concreta (Guerriero, Arciere),
     * che definirà come scalano le statistiche in base all'eroe scelto.
     */
    protected abstract void onLevelUp();

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

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public int getExpToNextLevel() {
        return expToNextLevel;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

}
