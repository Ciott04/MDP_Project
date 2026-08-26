package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Entità che può essere curata.
 */
public interface Healable {
    void heal(int amount);
    int getCurrentHp();
    int getMaxHp();
}
