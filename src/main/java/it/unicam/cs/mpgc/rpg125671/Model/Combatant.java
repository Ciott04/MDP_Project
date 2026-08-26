package it.unicam.cs.mpgc.rpg125671.Model;

/**
 * Entità che può partecipare al combattimento.
 */
public interface Combatant {
    String getName();
    int getAttack();
    int getDefense();
    int getSpeed();
    int getCurrentHp();
    boolean isAlive();
    void takeDamage(int finalDamage);
}
