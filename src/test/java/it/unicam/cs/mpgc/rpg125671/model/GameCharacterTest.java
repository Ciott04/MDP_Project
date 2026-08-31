package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterTest {

    @Test
    @DisplayName("Il costruttore valida i parametri")
    void costruttoreValidaParametri() {
        assertThrows(IllegalArgumentException.class, () -> new Warrior(null));
        assertThrows(IllegalArgumentException.class, () -> new Warrior(""));
        assertThrows(IllegalArgumentException.class, () -> new Warrior("   "));
        
        assertThrows(IllegalArgumentException.class, () -> new GameCharacter("Test", 0, 10, 10, 10) {});
        assertThrows(IllegalArgumentException.class, () -> new GameCharacter("Test", 10, -1, 10, 10) {});
    }

    @Test
    @DisplayName("takeDamage riduce gli HP correttamente e non va sotto 0")
    void takeDamageRiduceHp() {
        Warrior w = new Warrior("Aragorn");
        int maxHp = w.getCurrentHp();
        
        w.takeDamage(20);
        assertEquals(maxHp - 20, w.getCurrentHp());
        
        w.takeDamage(maxHp);
        assertEquals(0, w.getCurrentHp());
        assertFalse(w.isAlive());
        
        assertThrows(IllegalArgumentException.class, () -> w.takeDamage(0));
        assertThrows(IllegalArgumentException.class, () -> w.takeDamage(-10));
    }

    @Test
    @DisplayName("isAlive restituisce true se HP > 0, false altrimenti")
    void isAliveFunzionaCorrettamente() {
        Warrior w = new Warrior("Aragorn");
        assertTrue(w.isAlive());
        
        w.takeDamage(w.getMaxHp());
        assertFalse(w.isAlive());
    }

    @Test
    @DisplayName("I getter restituiscono i valori iniziali corretti")
    void getterRestituisconoValoriIniziali() {
        Warrior w = new Warrior("Aragorn");
        assertEquals("Aragorn", w.getName());
        assertEquals(120, w.getMaxHp());
        assertEquals(120, w.getCurrentHp());
        assertEquals(15, w.getAttack());
        assertEquals(15, w.getDefense());
        assertEquals(5, w.getSpeed());
    }
}
