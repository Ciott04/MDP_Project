package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Classe base astratta per i personaggi controllati dal giocatore.
 * Estende {@link GameCharacter} aggiungendo il sistema di progressione
 * (esperienza e livelli) e l'inventario.
 * Le sottoclassi ({@link Warrior}, {@link Archer}) definiscono le statistiche
 * base e lo scaling al level-up tramite {@link #onLevelUp()}.
 */
public abstract class Hero extends GameCharacter implements Healable {

    private final Inventory inventory;
    private int expToNextLevel;
    private int currentExp;
    private int level;

    protected Hero(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.inventory = new Inventory();
        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;
    }

    protected Hero(String name, int maxHp, int currentHp, int attack, int defense, int speed,
                   int level, int currentExp, int expToNextLevel) {
        super(name, maxHp, currentHp, attack, defense, speed);
        this.inventory = new Inventory();
        this.level = level;
        this.currentExp = currentExp;
        this.expToNextLevel = expToNextLevel;
    }

    /**
     * Ripristina gli HP dell'eroe della quantità specificata,
     * senza superare gli HP massimi.
     *
     * @param amount la quantità di HP da ripristinare; deve essere maggiore di zero.
     * @throws IllegalArgumentException se {@code amount} è minore o uguale a zero.
     */
    @Override
    public void heal(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("La cura deve essere maggiore di 0.");
        restoreHp(amount);
    }

    /**
     * Aggiunge punti esperienza all'eroe.
     * Se i punti accumulati raggiungono {@code expToNextLevel}, l'eroe sale di livello
     * (anche più volte se l'exp guadagnata è abbondante).
     * Ogni level-up moltiplica la soglia per 1.5 e chiama {@link #onLevelUp()}.
     *
     * @param exp i punti esperienza da aggiungere; deve essere maggiore di zero.
     * @throws IllegalArgumentException se {@code exp} è minore o uguale a zero.
     */
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

    public Inventory getInventory() { return  this.inventory; }

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
