package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Contratto per qualsiasi entità che può partecipare al combattimento.
 * Implementato da {@link GameCharacter} e da tutte le sue sottoclassi
 * ({@link Hero}, {@link Monster}, {@link Boss}).
 */
public interface Combatant {

    /** @return il nome dell'entità. */
    String getName();

    /** @return il valore di attacco. */
    int getAttack();

    /** @return il valore di difesa. */
    int getDefense();

    /** @return il valore di velocità, che determina l'ordine dei turni. */
    int getSpeed();

    /** @return i punti vita correnti. */
    int getCurrentHp();

    /** @return {@code true} se i punti vita correnti sono maggiori di zero. */
    boolean isAlive();

    /**
     * Applica un danno già calcolato all'entità.
     * I punti vita non scendono sotto zero.
     *
     * @param finalDamage il danno da applicare; deve essere maggiore di zero.
     * @throws IllegalArgumentException se {@code finalDamage} è minore o uguale a zero.
     */
    void takeDamage(int finalDamage);
}
