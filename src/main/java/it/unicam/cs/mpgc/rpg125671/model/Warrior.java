package it.unicam.cs.mpgc.rpg125671.model;

public class Warrior extends Hero {

    private static final int BASE_HP = 120;
    private static final int BASE_ATTACK = 15;
    private static final int BASE_DEFENSE = 15;
    private static final int BASE_SPEED = 5;

    private static final int HP_LEVEL_UP = 20;
    private static final int ATTACK_LEVEL_UP = 4;
    private static final int DEFENSE_LEVEL_UP = 3;
    private static final int SPEED_LEVEL_UP = 2;

    public Warrior(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPEED);
    }

    @Override
    protected void onLevelUp() {
        increaseMaxHp(HP_LEVEL_UP);
        increaseAttack(ATTACK_LEVEL_UP);
        increaseDefense(DEFENSE_LEVEL_UP);
        increaseSpeed(SPEED_LEVEL_UP);
    }
}
