package it.unicam.cs.mpgc.rpg125671.model;

public abstract class Hero extends GameCharacter implements Healable {

    private int expToNextLevel;
    private int currentExp;
    private int level;

    protected Hero(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;
    }

    @Override
    public void heal(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("La cura deve essere maggiore di 0.");
        restoreHp(amount);
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

    // --- GETTER ---

    public int getLevel() {
        return level;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public int getExpToNextLevel() {
        return expToNextLevel;
    }
}
