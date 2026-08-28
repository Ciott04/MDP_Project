package it.unicam.cs.mpgc.rpg125671.model;

public class Monster extends GameCharacter {

    private final int expReward;

    public Monster(String name, int maxHp, int attack, int defense, int speed, int expReward) {
        super(name, maxHp, attack, defense, speed);
        if (expReward <= 0)
            throw new IllegalArgumentException("La ricompensa in esperienza deve essere maggiore di 0.");
        this.expReward = expReward;
    }

    public int getExpReward() { return expReward; }
}
