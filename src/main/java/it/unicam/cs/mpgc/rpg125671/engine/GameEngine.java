package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.*;
import it.unicam.cs.mpgc.rpg125671.persistence.GameSave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private GameEngine(Hero hero, GameMap map, GameState state) {
        this.hero = hero;
        this.map = map;
        this.state = state;
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

    // --- SALVATAGGIO ---

    public GameSave toSave() {
        if (state == GameState.IN_COMBAT)
            throw new IllegalStateException("Non puoi salvare durante un combattimento.");

        String heroType = hero instanceof Warrior ? "WARRIOR" : "ARCHER";
        GameSave.HeroData heroData = new GameSave.HeroData(
                heroType, hero.getName(), hero.getMaxHp(), hero.getCurrentHp(),
                hero.getAttack(), hero.getDefense(), hero.getSpeed(),
                hero.getLevel(), hero.getCurrentExp(), hero.getExpToNextLevel(),
                hero.getInventory().getSummary()
        );

        List<GameSave.RoomData> roomDataList = new ArrayList<>();
        for (Room room : map.getRooms()) {
            GameSave.MonsterData monsterData = null;
            if (room.getMonster() != null) {
                Monster m = room.getMonster();
                monsterData = new GameSave.MonsterData(
                        m.getName(), m.getMaxHp(), m.getAttack(), m.getDefense(),
                        m.getSpeed(), m.getExpReward(), m instanceof Boss
                );
            }
            String rewardName = room.getReward() != null ? room.getReward().getName() : null;
            roomDataList.add(new GameSave.RoomData(
                    room.getType().name(), monsterData, rewardName, room.isCompleted()
            ));
        }

        GameSave.MapData mapData = new GameSave.MapData(roomDataList, map.getCurrentRoomIndex());
        return new GameSave(heroData, mapData, state.name());
    }

    public static GameEngine fromSave(GameSave save) {
        GameSave.HeroData h = save.hero();
        Hero hero;
        if ("WARRIOR".equals(h.type())) {
            hero = new Warrior(h.name(), h.maxHp(), h.currentHp(), h.attack(), h.defense(),
                    h.speed(), h.level(), h.currentExp(), h.expToNextLevel());
        } else {
            hero = new Archer(h.name(), h.maxHp(), h.currentHp(), h.attack(), h.defense(),
                    h.speed(), h.level(), h.currentExp(), h.expToNextLevel());
        }

        for (Map.Entry<String, Integer> entry : h.inventory().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                if ("Pozione curativa".equals(entry.getKey())) {
                    hero.getInventory().addItem(new HealingPotion());
                }
            }
        }

        List<Room> rooms = new ArrayList<>();
        for (GameSave.RoomData rd : save.map().rooms()) {
            RoomType type = RoomType.valueOf(rd.type());
            Room room = switch (type) {
                case MONSTER -> {
                    GameSave.MonsterData md = rd.monster();
                    yield Room.monster(new Monster(md.name(), md.maxHp(), md.attack(),
                            md.defense(), md.speed(), md.expReward()));
                }
                case BOSS -> {
                    GameSave.MonsterData md = rd.monster();
                    yield Room.boss(new Boss(md.name(), md.maxHp(), md.attack(),
                            md.defense(), md.speed(), md.expReward()));
                }
                case TREASURE -> Room.treasure(new HealingPotion());
                case EMPTY -> Room.empty();
            };
            if (rd.completed()) room.complete();
            rooms.add(room);
        }

        GameMap map = new GameMap(rooms, save.map().currentRoomIndex());
        GameState state = GameState.valueOf(save.gameState());
        return new GameEngine(hero, map, state);
    }

    // --- GETTER ---

    public Hero getHero() { return hero; }
    public GameMap getMap() { return map; }
    public GameState getState() { return state; }
    public CombatEngine getCurrentCombat() { return currentCombat; }
}
