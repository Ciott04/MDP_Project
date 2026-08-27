package it.unicam.cs.mpgc.rpg125671.model;

public class Archer extends Hero {

    private static final int BASE_HP      = 90;
    private static final int BASE_ATTACK  = 14;
    private static final int BASE_DEFENSE = 6;
    private static final int BASE_SPEED   = 12;

    private static final int HP_LEVEL_UP = 8;
    private static final int ATTACK_LEVEL_UP = 3;
    private static final int DEFENSE_LEVEL_UP = 1;
    private static final int SPEED_LEVEL_UP = 4;

    public Archer(String name) {
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
