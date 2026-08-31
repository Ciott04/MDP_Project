package it.unicam.cs.mpgc.rpg125671.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    @DisplayName("addItem e hasItem funzionano correttamente")
    void addItemHasItem() {
        Inventory inv = new Inventory();
        Item potion = new HealingPotion();
        
        assertFalse(inv.hasItem("Pozione curativa"));
        
        inv.addItem(potion);
        assertTrue(inv.hasItem("Pozione curativa"));
        assertEquals(1, inv.getItemCount("Pozione curativa"));
    }

    @Test
    @DisplayName("removeItem rimuove l'ultimo elemento e lancia eccezione se vuoto")
    void removeItem() {
        Inventory inv = new Inventory();
        Item potion1 = new HealingPotion();
        Item potion2 = new HealingPotion();
        
        inv.addItem(potion1);
        inv.addItem(potion2);
        
        Item removed = inv.removeItem("Pozione curativa");
        assertNotNull(removed);
        assertEquals(1, inv.getItemCount("Pozione curativa"));
        
        inv.removeItem("Pozione curativa");
        assertEquals(0, inv.getItemCount("Pozione curativa"));
        assertFalse(inv.hasItem("Pozione curativa"));
        
        assertThrows(RuntimeException.class, () -> inv.removeItem("Pozione curativa"));
        assertThrows(RuntimeException.class, () -> inv.removeItem("OggettoInesistente"));
    }

    @Test
    @DisplayName("getSummary restituisce una mappa immutabile corretta")
    void getSummaryImmutabile() {
        Inventory inv = new Inventory();
        inv.addItem(new HealingPotion());
        inv.addItem(new HealingPotion());
        
        Map<String, Integer> summary = inv.getSummary();
        assertEquals(1, summary.size());
        assertEquals(2, summary.get("Pozione curativa"));
        
        assertThrows(UnsupportedOperationException.class, () -> summary.put("Altro", 1));
    }
}
