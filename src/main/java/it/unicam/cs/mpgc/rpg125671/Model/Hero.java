package it.unicam.cs.mpgc.rpg125671.Model;

public abstract class Hero {

    private final String name;

    private int maxHp;
    private int currentHp;

    private int expToNextLevel;
    private int currentExp;
    private int level;

    private int att;

    private int def;

    private int speed;

    public Hero(String name, int maxHp, int att, int def, int speed) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        if (maxHp <= 0)
            throw new IllegalArgumentException("I punti vita massimi devono essere maggiori di 0.");
        if (att <= 0 || def <= 0 || speed <= 0)
            throw new IllegalArgumentException("Gli attributi dell'eroe non possono essere negativi.");

        this.name = name;
        this.maxHp = maxHp;
        currentHp = maxHp;
        this.att = att;
        this.def = def;
        this.speed = speed;

        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;

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

    protected void increaseAtt(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento dell'attacco deve essere maggiore di 0.");
        this.att += amount;
    }

    protected void increaseDef(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento dell'attacco deve essere maggiore di 0.");
        this.def += amount;
    }

    protected void increaseSpeed(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("L'incremento dell'attacco deve essere maggiore di 0.");
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

    public int getAtt() {
        return att;
    }

    public int getDef() {
        return def;
    }

    public int getSpeed() {
        return speed;
    }

}
