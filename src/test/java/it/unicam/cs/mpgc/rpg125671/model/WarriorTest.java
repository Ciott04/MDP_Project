package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    @Test
    @DisplayName("Le statistiche iniziali corrispondono alle costanti")
    void statisticheInizialiCorrette() {
        Warrior w = new Warrior("Gimli");
        assertEquals("Gimli", w.getName());
        assertEquals(120, w.getMaxHp());
        assertEquals(120, w.getCurrentHp());
        assertEquals(15, w.getAttack());
        assertEquals(15, w.getDefense());
        assertEquals(5, w.getSpeed());
    }

    @Test
    @DisplayName("onLevelUp aumenta le statistiche correttamente")
    void onLevelUpAumentaStatistiche() {
        Warrior w = new Warrior("Gimli");
        w.gainExp(100);
        
        assertEquals(140, w.getMaxHp());
        assertEquals(120, w.getCurrentHp());
        assertEquals(19, w.getAttack());
        assertEquals(18, w.getDefense());
        assertEquals(7, w.getSpeed());
    }
}
