package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Contratto per le entità che possono ricevere cure.
 * Separato da {@link Combatant} per rispettare il principio ISP:
 * non tutti i combattenti sono curabili (es. i mostri normali non lo sono).
 * Implementato da {@link Hero} e {@link Boss}.
 */
public interface Healable {

    /**
     * Ripristina i punti vita dell'entità.
     * I punti vita non possono superare il massimo.
     *
     * @param amount la quantità di HP da ripristinare; deve essere maggiore di zero.
     * @throws IllegalArgumentException se {@code amount} è minore o uguale a zero.
     */
    void heal(int amount);

    /** @return i punti vita correnti. */
    int getCurrentHp();

    /** @return i punti vita massimi. */
    int getMaxHp();
}
