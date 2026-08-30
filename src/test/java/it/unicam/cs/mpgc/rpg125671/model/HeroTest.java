package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    @DisplayName("heal ripristina gli HP ma non supera il massimo")
    void healRipristinaHp() {
        Warrior w = new Warrior("Aragorn");
        int max = w.getMaxHp();
        
        w.takeDamage(50);
        w.heal(30);
        assertEquals(max - 20, w.getCurrentHp());
        
        w.heal(50);
        assertEquals(max, w.getCurrentHp());
        
        assertThrows(IllegalArgumentException.class, () -> w.heal(0));
        assertThrows(IllegalArgumentException.class, () -> w.heal(-10));
    }

    @Test
    @DisplayName("gainExp aumenta l'esperienza e fa salire di livello")
    void gainExpAumentaEsperienza() {
        Warrior w = new Warrior("Aragorn");
        assertEquals(1, w.getLevel());
        
        w.gainExp(50);
        assertEquals(1, w.getLevel());
        
        w.gainExp(60); 
        assertEquals(2, w.getLevel());
        
        assertThrows(IllegalArgumentException.class, () -> w.gainExp(0));
        assertThrows(IllegalArgumentException.class, () -> w.gainExp(-10));
    }
    
    @Test
    @DisplayName("gainExp fa salire di livello più volte se exp è sufficiente")
    void gainExpLivelliMultipli() {
        Warrior w = new Warrior("Aragorn");
        w.gainExp(300);
        assertTrue(w.getLevel() >= 3);
    }

    @Test
    @DisplayName("L'inventario è inizializzato correttamente")
    void inventarioInizializzato() {
        Warrior w = new Warrior("Aragorn");
        assertNotNull(w.getInventory());
    }
}
