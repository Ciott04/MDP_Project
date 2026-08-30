package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {

    @Test
    @DisplayName("Boss estende Monster e gestisce le cure")
    void healFunziona() {
        Boss b = new Boss("Drago", 200, 30, 20, 15, 100);
        b.takeDamage(100);
        assertEquals(100, b.getCurrentHp());
        
        b.heal(50);
        assertEquals(150, b.getCurrentHp());
        
        assertThrows(IllegalArgumentException.class, () -> b.heal(-10));
    }

    @Test
    @DisplayName("tryEmergencyHeal si attiva sotto il 30% e una sola volta")
    void tryEmergencyHealFunziona() {
        Boss b = new Boss("Drago", 200, 30, 20, 15, 100);
        
        b.takeDamage(100); 
        assertFalse(b.tryEmergencyHeal());
        assertEquals(100, b.getCurrentHp());
        
        b.takeDamage(50); 
        assertTrue(b.tryEmergencyHeal());
        assertEquals(90, b.getCurrentHp()); 
        
        b.takeDamage(50); 
        assertFalse(b.tryEmergencyHeal()); 
        assertEquals(40, b.getCurrentHp());
    }
}
