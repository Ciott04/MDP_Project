package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProceduralMapGenerator implements MapGenerator {

    private static final String[] MONSTER_NAMES = {
            "Goblin", "Scheletro", "Orco", "Spettro", "Troll"
    };

    private static final String[] BOSS_NAMES = {
            "Drago Oscuro", "Hacerle", "Foil Cain"
    };

    private static final int MONSTER_CHANCE = 60;
    private static final int TREASURE_CHANCE = 25;

    private final int roomCount;
    private final Random random;

    public ProceduralMapGenerator(int roomCount, long seed) {
        if (roomCount <  2)
            throw new IllegalArgumentException("La mappa deve avere almeno 2 stanze.");
        this.roomCount = roomCount;
        this.random = new Random(seed);
    }

    public ProceduralMapGenerator(int roomCount) {
        if (roomCount <  2)
            throw new IllegalArgumentException("La mappa deve avere almeno 2 stanze.");
        this.roomCount = roomCount;
        this.random = new Random();
    }

    @Override
    public GameMap generate() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < roomCount - 1; i++) {
            rooms.add(generateRoom(i));
        }
        rooms.add(generateBossRoom());
        return new GameMap(rooms);
    }

    private Room generateRoom(int depth) {
        int roll = random.nextInt(100);
        if (roll < MONSTER_CHANCE)
            return Room.monster(generateMonster(depth));
        else if (roll < MONSTER_CHANCE + TREASURE_CHANCE)
            return Room.treasure(new HealingPotion());
        else
            return Room.empty();
    }

    private Monster generateMonster(int depth) {
        String name = MONSTER_NAMES[random.nextInt(MONSTER_NAMES.length)];
        int hp = 40 + depth * 10;
        int attack = 8 + depth * 2;
        int defense = 3 + depth;
        int speed = 4 + depth;
        int expReward = 20 + depth * 10;
        return new Monster(name, hp, attack, defense, speed, expReward);
    }

    private Room generateBossRoom() {
        String bossName = BOSS_NAMES[random.nextInt(BOSS_NAMES.length)];
        Boss boss = new Boss(bossName, 200, 25, 15, 8, 150);
        return Room.boss(boss);
    }
}
