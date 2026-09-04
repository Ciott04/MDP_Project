package it.unicam.cs.mpgc.rpg125671.persistence;

import java.util.List;
import java.util.Map;

public record GameSave(
        HeroData hero,
        MapData map,
        String gameState
) {
    public record HeroData(
            String type,
            String name,
            int maxHp,
            int currentHp,
            int attack,
            int defense,
            int speed,
            int level,
            int currentExp,
            int expToNextLevel,
            Map<String, Integer> inventory
    ) {}

    public record MapData(
            List<RoomData> rooms,
            int currentRoomIndex
    ) {}

    public record RoomData(
            String type,
            MonsterData monster,
            String rewardItemName,
            boolean completed
    ) {}

    public record MonsterData(
            String name,
            int maxHp,
            int attack,
            int defense,
            int speed,
            int expReward,
            boolean isBoss
    ) {}
}
