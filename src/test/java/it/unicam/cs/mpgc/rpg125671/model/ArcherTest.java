package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcherTest {

    @Test
    @DisplayName("Le statistiche iniziali corrispondono alle costanti")
    void statisticheInizialiCorrette() {
        Archer a = new Archer("Legolas");
        assertEquals("Legolas", a.getName());
        assertEquals(90, a.getMaxHp());
        assertEquals(90, a.getCurrentHp());
        assertEquals(14, a.getAttack());
        assertEquals(6, a.getDefense());
        assertEquals(12, a.getSpeed());
    }

    @Test
    @DisplayName("onLevelUp aumenta le statistiche correttamente")
    void onLevelUpAumentaStatistiche() {
        Archer a = new Archer("Legolas");
        a.gainExp(100);
        
        assertEquals(98, a.getMaxHp());
        assertEquals(90, a.getCurrentHp());
        assertEquals(17, a.getAttack());
        assertEquals(7, a.getDefense());
        assertEquals(16, a.getSpeed());
    }
}
