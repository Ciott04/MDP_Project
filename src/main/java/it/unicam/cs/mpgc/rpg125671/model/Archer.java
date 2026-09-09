package it.unicam.cs.mpgc.rpg125671.model;

public class Archer extends Hero {

    private static final int BASE_HP      = 100;
    private static final int BASE_ATTACK  = 16;
    private static final int BASE_DEFENSE = 10;
    private static final int BASE_SPEED   = 12;

    private static final int HP_LEVEL_UP = 8;
    private static final int ATTACK_LEVEL_UP = 3;
    private static final int DEFENSE_LEVEL_UP = 2;
    private static final int SPEED_LEVEL_UP = 4;

    public Archer(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
    }

    public Archer(String name, int maxHp, int currentHp, int attack, int defense, int speed,
                  int level, int currentExp, int expToNextLevel) {
        super(name, maxHp, currentHp, attack, defense, speed, level, currentExp, expToNextLevel);
    }

    @Override
    protected void onLevelUp() {
        increaseMaxHp(HP_LEVEL_UP);
        increaseAttack(ATTACK_LEVEL_UP);
        increaseDefense(DEFENSE_LEVEL_UP);
        increaseSpeed(SPEED_LEVEL_UP);
    }

    // --- GETTER STATISTICHE BASE ---

    public static int getBaseHp() {
        return BASE_HP;
    }

    public static int getBaseAttack() {
        return BASE_ATTACK;
    }

    public static int getBaseDefense() {
        return BASE_DEFENSE;
    }

    public static int getBaseSpeed() {
        return BASE_SPEED;
    }

    // --- GETTER STATISTICHE LEVEL UP ---

    public static int getHpLevelUp() {
        return HP_LEVEL_UP;
    }

    public static int getAttackLevelUp() {
        return ATTACK_LEVEL_UP;
    }

    public static int getDefenseLevelUp() {
        return DEFENSE_LEVEL_UP;
    }

    public static int getSpeedLevelUp() {
        return SPEED_LEVEL_UP;
    }
}
