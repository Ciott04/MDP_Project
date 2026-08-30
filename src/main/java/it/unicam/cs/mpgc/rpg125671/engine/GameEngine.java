package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.GameMap;
import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.Room;
import it.unicam.cs.mpgc.rpg125671.model.RoomType;

public class GameEngine {

    private final Hero hero;
    private final GameMap map;
    private GameState state;
    private CombatEngine currentCombat;

    public GameEngine(Hero hero, MapGenerator mapGenerator) {
        if (hero == null)
            throw new IllegalArgumentException("L'eroe non può essere null.");
        if (mapGenerator == null)
            throw new IllegalArgumentException("Il generatore di mappa non può essere null.");
        this.hero = hero;
        this.map = mapGenerator.generate();
        this.state = GameState.EXPLORING;
        this.currentCombat = null;
    }

    public Room enterCurrentRoom() {
        if (state != GameState.EXPLORING)
            throw new IllegalStateException("Non puoi entrare in una stanza ora.");

        Room room = map.getCurrentRoom();

        if (room.isCompleted())
            throw new IllegalStateException("Questa stanza è già stata completata.");

        switch (room.getType()) {
            case MONSTER, BOSS -> {
                currentCombat = new CombatEngine(hero, room.getMonster());
                state = GameState.IN_COMBAT;
            }
            case TREASURE -> {
                hero.getInventory().addItem(room.getReward());
                room.complete();
                updateStateAfterRoomCompleted(room);
            }
            case EMPTY -> {
                room.complete();
                updateStateAfterRoomCompleted(room);
            }
        }
        return room;
    }

    public TurnResult executeCombatTurn(CombatAction action) {
        if (state != GameState.IN_COMBAT)
            throw new IllegalStateException("Non sei in combattimento.");
        TurnResult result = currentCombat.executeTurn(action);

        if (result.combatResult() == CombatResult.HERO_WON) {
            map.getCurrentRoom().complete();
            currentCombat = null;
            updateStateAfterRoomCompleted(map.getCurrentRoom());
        }
        else if (result.combatResult() == CombatResult.HERO_LOST) {
            currentCombat = null;
            state = GameState.GAME_OVER;
        }
        return result;
    }

    public Room advanceToNextRoom() {
        if (state != GameState.ROOM_COMPLETED)
            throw new IllegalStateException("Non puoi avanzare alla prossima stanza ora.");
        if (!map.hasNextRoom())
            throw new IllegalStateException("Non ci sono altre stanze.");

        map.advanceToNextRoom();
        state = GameState.EXPLORING;
        return map.getCurrentRoom();
    }

    private void updateStateAfterRoomCompleted(Room room) {
        if (room.getType() == RoomType.BOSS)
            state = GameState.GAME_WON;
        else
            state = GameState.ROOM_COMPLETED;
    }

    // --- GETTER ---

    public Hero getHero() { return hero; }
    public GameMap getMap() { return map; }
    public GameState getState() { return state; }
    public CombatEngine getCurrentCombat() { return currentCombat; }
}
