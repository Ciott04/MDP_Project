package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.Room;
import it.unicam.cs.mpgc.rpg125671.model.RoomType;
import it.unicam.cs.mpgc.rpg125671.model.Warrior;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    @DisplayName("Il costruttore lancia eccezione con parametri nulli")
    void testCostruttoreConParametriNulli() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5);
        
        assertThrows(IllegalArgumentException.class, () -> new GameEngine(null, generator));
        assertThrows(IllegalArgumentException.class, () -> new GameEngine(hero, null));
    }

    @Test
    @DisplayName("Lo stato iniziale è EXPLORING")
    void testStatoInizialeExploring() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5);
        GameEngine engine = new GameEngine(hero, generator);
        
        assertEquals(GameState.EXPLORING, engine.getState());
        assertNull(engine.getCurrentCombat());
    }

    @Test
    @DisplayName("L'ingresso in una stanza MONSTER imposta lo stato IN_COMBAT")
    void testIngressoStanzaMonster() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5, 42L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        // Cerca una stanza mostro scartando le altre
        while(engine.getMap().getCurrentRoom().getType() != RoomType.MONSTER) {
            engine.getMap().advanceToNextRoom();
        }
        
        Room room = engine.enterCurrentRoom();
        assertEquals(RoomType.MONSTER, room.getType());
        assertEquals(GameState.IN_COMBAT, engine.getState());
        assertNotNull(engine.getCurrentCombat());
    }

    @Test
    @DisplayName("L'ingresso in una stanza TREASURE aggiunge oggetto e imposta ROOM_COMPLETED")
    void testIngressoStanzaTreasure() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5, 42L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        // Cerca una stanza tesoro
        while(engine.getMap().getCurrentRoom().getType() != RoomType.TREASURE) {
            if(!engine.getMap().hasNextRoom()) return; 
            engine.getMap().advanceToNextRoom();
        }
        
        int potCount = hero.getInventory().getItemCount("Pozione curativa");
        Room room = engine.enterCurrentRoom();
        
        assertEquals(RoomType.TREASURE, room.getType());
        assertEquals(GameState.ROOM_COMPLETED, engine.getState());
        assertEquals(potCount + 1, hero.getInventory().getItemCount("Pozione curativa"));
        assertTrue(room.isCompleted());
    }

    @Test
    @DisplayName("L'ingresso in una stanza EMPTY imposta ROOM_COMPLETED")
    void testIngressoStanzaEmpty() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(15, 123L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        // Cerca una stanza vuota
        while(engine.getMap().getCurrentRoom().getType() != RoomType.EMPTY) {
            if(!engine.getMap().hasNextRoom()) return; 
            engine.getMap().advanceToNextRoom();
        }
        
        Room room = engine.enterCurrentRoom();
        
        assertEquals(RoomType.EMPTY, room.getType());
        assertEquals(GameState.ROOM_COMPLETED, engine.getState());
        assertTrue(room.isCompleted());
    }

    @Test
    @DisplayName("executeCombatTurn fuori dal combattimento lancia eccezione")
    void testExecuteCombatTurnFuoriCombattimentoLanciaEccezione() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5);
        GameEngine engine = new GameEngine(hero, generator); // EXPLORING
        
        assertThrows(IllegalStateException.class, () -> engine.executeCombatTurn(CombatAction.ATTACK));
    }

    @Test
    @DisplayName("advanceToNextRoom da ROOM_COMPLETED funziona correttamente")
    void testAdvanceToNextRoomDaRoomCompleted() {
        Hero hero = new Warrior("Eroe");
        hero.gainExp(1000); // Per essere sicuri che non muoia
        MapGenerator generator = new ProceduralMapGenerator(5, 1L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        if (engine.getMap().getCurrentRoom().getType() != RoomType.MONSTER && engine.getMap().getCurrentRoom().getType() != RoomType.BOSS) {
            engine.enterCurrentRoom();
        } else {
            engine.enterCurrentRoom();
            while(engine.getState() == GameState.IN_COMBAT) {
                engine.executeCombatTurn(CombatAction.ATTACK);
            }
        }
        
        if (engine.getState() == GameState.ROOM_COMPLETED) {
            Room nextRoom = engine.advanceToNextRoom();
            assertEquals(GameState.EXPLORING, engine.getState());
            assertNotNull(nextRoom);
        }
    }

    @Test
    @DisplayName("advanceToNextRoom non da ROOM_COMPLETED lancia eccezione")
    void testAdvanceToNextRoomNonDaRoomCompletedLanciaEccezione() {
        Hero hero = new Warrior("Eroe");
        MapGenerator generator = new ProceduralMapGenerator(5);
        GameEngine engine = new GameEngine(hero, generator); // EXPLORING
        
        assertThrows(IllegalStateException.class, engine::advanceToNextRoom);
    }

    @Test
    @DisplayName("Flusso completo: l'eroe esplora, sconfigge il boss -> GAME_WON")
    void testFlussoCompletoSconfiggeBoss() {
        Hero hero = new Warrior("Eroe Forte");
        hero.gainExp(1000); // Aumenta di livello per non morire
        MapGenerator generator = new ProceduralMapGenerator(2, 42L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        while(engine.getState() != GameState.GAME_WON && engine.getState() != GameState.GAME_OVER) {
            if (engine.getState() == GameState.EXPLORING) {
                engine.enterCurrentRoom();
            } else if (engine.getState() == GameState.IN_COMBAT) {
                engine.executeCombatTurn(CombatAction.ATTACK);
            } else if (engine.getState() == GameState.ROOM_COMPLETED) {
                engine.advanceToNextRoom();
            }
        }
        
        assertEquals(GameState.GAME_WON, engine.getState());
    }

    @Test
    @DisplayName("L'eroe muore in combattimento -> GAME_OVER")
    void testEroeMuoreInCombattimento() {
        Hero hero = new Warrior("Eroe Debole");
        hero.takeDamage(119); // Ha solo 1 hp rimanente
        MapGenerator generator = new ProceduralMapGenerator(5, 42L); 
        GameEngine engine = new GameEngine(hero, generator);
        
        while(engine.getMap().getCurrentRoom().getType() != RoomType.MONSTER && engine.getMap().getCurrentRoom().getType() != RoomType.BOSS) {
            engine.getMap().advanceToNextRoom();
        }
        
        engine.enterCurrentRoom();
        
        while(engine.getState() == GameState.IN_COMBAT) {
            engine.executeCombatTurn(CombatAction.ATTACK);
        }
        
        assertEquals(GameState.GAME_OVER, engine.getState());
    }
}
