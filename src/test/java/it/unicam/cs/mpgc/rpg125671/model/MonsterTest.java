package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    @DisplayName("Il costruttore valida i parametri")
    void costruttoreValidaParametri() {
        Monster m = new Monster("Goblin", 50, 10, 5, 8, 20);
        assertEquals("Goblin", m.getName());
        assertEquals(20, m.getExpReward());
        
        assertThrows(IllegalArgumentException.class, () -> new Monster("Goblin", 50, 10, 5, 8, 0));
        assertThrows(IllegalArgumentException.class, () -> new Monster("Goblin", 50, 10, 5, 8, -10));
    }

    @Test
    @DisplayName("Il mostro può subire danni e morire")
    void mostroPuoMorire() {
        Monster m = new Monster("Goblin", 50, 10, 5, 8, 20);
        assertTrue(m.isAlive());
        
        m.takeDamage(50);
        assertFalse(m.isAlive());
        assertEquals(0, m.getCurrentHp());
    }
}
