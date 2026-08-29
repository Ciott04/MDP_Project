package it.unicam.cs.mpgc.rpg125671.model;

public class Room {

    private final RoomType type;
    private final Monster monster;
    private final Item reward;
    private boolean completed;

    private Room(RoomType type, Monster monster, Item reward) {
        this.type = type;
        this.monster = monster;
        this.reward = reward;
        this.completed = false;
    }

    public static Room monster(Monster monster) {
        if (monster == null)
            throw new IllegalArgumentException("Il mostro non può essere null.");
        return new Room(RoomType.MONSTER, monster, null);
    }

    public static Room treasure(Item reward) {
        if (reward == null)
            throw new IllegalArgumentException("La ricompensa non può essere null.");
        return new Room(RoomType.TREASURE, null, reward);
    }

    public static Room empty() {
        return new Room(RoomType.EMPTY, null, null);
    }

    public static Room boss(Boss boss) {
        if (boss == null)
            throw new IllegalArgumentException("Il boss non può essere null.");
        return new Room(RoomType.BOSS, boss, null);
    }

    public void complete() {
        this.completed = true;
    }

    // --- GETTER ---

    public RoomType getType() { return type; }
    public Monster getMonster() { return monster; }
    public Item getReward() { return reward; }
    public boolean isCompleted() { return completed; }
}
