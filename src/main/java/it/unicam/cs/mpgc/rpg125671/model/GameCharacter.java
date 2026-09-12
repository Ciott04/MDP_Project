package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Classe base astratta per tutte le entità di gioco (eroi e mostri).
 * Incapsula lo stato comune (nome, HP, statistiche di combattimento) e
 * fornisce metodi protetti per modificarlo in modo controllato.
 * Implementa {@link Combatant} delegando le operazioni di combattimento
 * alle sottoclassi tramite template method.
 */
public abstract class GameCharacter implements Combatant {

    private final String name;

    private int maxHp;
    private int currentHp;

    private int attack;

    private int defense;

    private int speed;

    /**
     * Crea una nuova entità con HP correnti uguali agli HP massimi.
     *
     * @param name     il nome dell'entità; non può essere null o vuoto.
     * @param maxHp    i punti vita massimi; deve essere maggiore di zero.
     * @param attack   il valore di attacco; deve essere maggiore di zero.
     * @param defense  il valore di difesa; deve essere maggiore di zero.
     * @param speed    il valore di velocità; deve essere maggiore di zero.
     * @throws IllegalArgumentException se uno dei parametri non è valido.
     */
    protected GameCharacter(String name, int maxHp, int attack, int defense, int speed) {
        this(name, maxHp, maxHp, attack, defense, speed);
    }

    /**
     * Costruttore di ripristino: usato per ricaricare uno stato salvato in cui
     * gli HP correnti possono differire dagli HP massimi.
     *
     * @param currentHp gli HP correnti al momento del salvataggio; deve essere in {@code [0, maxHp]}.
     * @throws IllegalArgumentException se {@code currentHp} è fuori dall'intervallo valido.
     */
    protected GameCharacter(String name, int maxHp, int currentHp, int attack, int defense, int speed) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        if (maxHp <= 0)
            throw new IllegalArgumentException("I punti vita massimi devono essere maggiori di 0.");
        if (currentHp < 0 || currentHp > maxHp)
            throw new IllegalArgumentException("I punti vita correnti non sono validi.");
        if (attack <= 0 || defense <= 0 || speed <= 0)
            throw new IllegalArgumentException("Gli attributi non possono essere negativi.");

        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
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

    /**
     * Ripristina gli HP correnti senza superare il massimo.
     * Metodo protetto: accessibile solo da {@link Hero#heal} e {@link Boss#heal},
     * che ne garantiscono la validazione prima della chiamata.
     *
     * @param amount la quantità di HP da ripristinare.
     */
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
