package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.GameMap;
import it.unicam.cs.mpgc.rpg125671.model.Room;
import it.unicam.cs.mpgc.rpg125671.model.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProceduralMapGeneratorTest {

    @Test
    @DisplayName("Il costruttore lancia eccezione se le stanze sono meno di 2")
    void testCostruttoreLanciaEccezioneConPocheStanze() {
        assertThrows(IllegalArgumentException.class, () -> new ProceduralMapGenerator(1));
        assertThrows(IllegalArgumentException.class, () -> new ProceduralMapGenerator(0, 123L));
    }

    @Test
    @DisplayName("La mappa generata ha il numero corretto di stanze")
    void testMappaGenerataHaNumeroCorrettoStanze() {
        ProceduralMapGenerator generator = new ProceduralMapGenerator(5, 123L);
        GameMap map = generator.generate();
        
        int count = 0;
        while(map.hasNextRoom()) {
            map.advanceToNextRoom();
            count++;
        }
        // Il costruttore di GameMap imposta la prima stanza (indice 0), quindi avanzo 4 volte. In totale 5 stanze.
        assertEquals(4, count);
    }
    
    @Test
    @DisplayName("L'ultima stanza è sempre di tipo BOSS")
    void testUltimaStanzaSempreBoss() {
        ProceduralMapGenerator generator = new ProceduralMapGenerator(5, 123L);
        GameMap map = generator.generate();
        
        Room lastRoom = map.getCurrentRoom();
        while(map.hasNextRoom()) {
            lastRoom = map.advanceToNextRoom();
        }
        
        assertEquals(RoomType.BOSS, lastRoom.getType());
        assertNotNull(lastRoom.getMonster());
    }

    @Test
    @DisplayName("Stesso seme produce la stessa mappa (deterministico)")
    void testGeneratoreDeterministicoConStessoSeme() {
        ProceduralMapGenerator gen1 = new ProceduralMapGenerator(5, 42L);
        ProceduralMapGenerator gen2 = new ProceduralMapGenerator(5, 42L);
        
        GameMap map1 = gen1.generate();
        GameMap map2 = gen2.generate();
        
        assertEquals(map1.getCurrentRoom().getType(), map2.getCurrentRoom().getType());
        while(map1.hasNextRoom() && map2.hasNextRoom()) {
            assertEquals(map1.advanceToNextRoom().getType(), map2.advanceToNextRoom().getType());
        }
    }

    @Test
    @DisplayName("Semi diversi producono mappe diverse")
    void testSemiDiversiProduconoMappeDiverse() {
        ProceduralMapGenerator gen1 = new ProceduralMapGenerator(10, 1L);
        ProceduralMapGenerator gen2 = new ProceduralMapGenerator(10, 2L);
        
        GameMap map1 = gen1.generate();
        GameMap map2 = gen2.generate();
        
        boolean diverse = false;
        
        if (map1.getCurrentRoom().getType() != map2.getCurrentRoom().getType()) {
            diverse = true;
        } else {
            while(map1.hasNextRoom() && map2.hasNextRoom()) {
                if (map1.advanceToNextRoom().getType() != map2.advanceToNextRoom().getType()) {
                    diverse = true;
                    break;
                }
            }
        }
        
        assertTrue(diverse, "Le mappe generate da semi diversi dovrebbero essere diverse");
    }

    @Test
    @DisplayName("Le stanze contengono oggetti o mostri validi in base al tipo")
    void testStanzeContengonoEntitaValide() {
        ProceduralMapGenerator generator = new ProceduralMapGenerator(10, 123L);
        GameMap map = generator.generate();
        
        Room current = map.getCurrentRoom();
        verificaStanza(current);
        
        while(map.hasNextRoom()) {
            verificaStanza(map.advanceToNextRoom());
        }
    }
    
    private void verificaStanza(Room room) {
        if (room.getType() == RoomType.MONSTER || room.getType() == RoomType.BOSS) {
            assertNotNull(room.getMonster());
        } else if (room.getType() == RoomType.TREASURE) {
            assertNotNull(room.getReward());
        } else { // EMPTY
            assertNull(room.getMonster());
            assertNull(room.getReward());
        }
    }
}
