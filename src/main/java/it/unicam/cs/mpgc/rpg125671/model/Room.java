package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Rappresenta una singola stanza del dungeon.
 * Il costruttore è privato: le stanze si creano tramite factory method statici
 * ({@link #monster}, {@link #treasure}, {@link #empty}, {@link #boss}),
 * che garantiscono la coerenza tra tipo e contenuto della stanza.
 */
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

    /**
     * Crea una stanza con un mostro da sconfiggere.
     *
     * @param monster il mostro presente nella stanza; non può essere null.
     * @throws IllegalArgumentException se {@code monster} è null.
     */
    public static Room monster(Monster monster) {
        if (monster == null)
            throw new IllegalArgumentException("Il mostro non può essere null.");
        return new Room(RoomType.MONSTER, monster, null);
    }

    /**
     * Crea una stanza con una ricompensa da raccogliere.
     *
     * @param reward l'oggetto presente nella stanza; non può essere null.
     * @throws IllegalArgumentException se {@code reward} è null.
     */
    public static Room treasure(Item reward) {
        if (reward == null)
            throw new IllegalArgumentException("La ricompensa non può essere null.");
        return new Room(RoomType.TREASURE, null, reward);
    }

    /** Crea una stanza vuota, senza mostro né ricompensa. */
    public static Room empty() {
        return new Room(RoomType.EMPTY, null, null);
    }

    /**
     * Crea la stanza del boss finale.
     *
     * @param boss il boss presente nella stanza; non può essere null.
     * @throws IllegalArgumentException se {@code boss} è null.
     */
    public static Room boss(Boss boss) {
        if (boss == null)
            throw new IllegalArgumentException("Il boss non può essere null.");
        return new Room(RoomType.BOSS, boss, null);
    }

    /** Segna la stanza come completata. */
    public void complete() {
        this.completed = true;
    }

    // --- GETTER ---

    public RoomType getType() { return type; }
    public Monster getMonster() { return monster; }
    public Item getReward() { return reward; }
    public boolean isCompleted() { return completed; }
}
