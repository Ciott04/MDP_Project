package it.unicam.cs.mpgc.rpg125671.model;

public class HealingPotion implements Item {

    private static final int HEAL_AMOUNT = 30;
    private static final String NAME = "Pozione curativa";
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void use(Hero hero) {
        hero.heal(HEAL_AMOUNT);
    }
}
