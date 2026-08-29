package it.unicam.cs.mpgc.rpg125671.model;

public class CombatEngine {

    private final Hero hero;
    private final Monster monster;
    private final boolean heroGoesFirst;

    public CombatEngine(Hero hero, Monster monster) {
        if (hero == null)
            throw new IllegalArgumentException("L'eroe non può essere null.");
        if (monster == null)
            throw new IllegalArgumentException("Il mostro non può essere null.");
        if (!hero.isAlive())
            throw new IllegalArgumentException("L'eroe deve essere vivo.");
        if (!monster.isAlive())
            throw new IllegalArgumentException("Il mostro deve essere vivo.");

        this.hero = hero;
        this.monster = monster;
        this.heroGoesFirst = this.hero.getSpeed() >= this.monster.getSpeed();
    }

    public TurnResult executeTurn(CombatAction heroAction) {
        if (isCombatOver())
            throw new IllegalArgumentException("Il combattimento è già terminato.");

        int damageToMonster = 0;
        int damageToHero = 0;
        int heroHealed = 0;
        int bossHealed = 0;

        if (isHeroFirst()) {
            if (heroAction == CombatAction.ATTACK)
                damageToMonster = applyAttack(hero, monster);
            else
                heroHealed = applyPotion();
        }
    }

    public int applyAttack(Combatant attacker, Combatant defender) {
        int damage = Math.max(1, attacker.getAttack() - defender.getDefense());
        defender.takeDamage(damage);
        return damage;
    }

    public int applyPotion() {
        String potionName = "Pozione curativa";
        if (!hero.getInventory().hasItem(potionName))
            throw new IllegalStateException(potionName + " non disponibile nell'inventario.");
        int hpBefore = hero.getCurrentHp();
        Item potion = hero.getInventory().removeItem(potionName);
        potion.use(hero);
        return hero.getCurrentHp() - hpBefore;
    }

    public boolean isCombatOver() {
        return !hero.isAlive() || !monster.isAlive();
    }

    public boolean isHeroFirst() {
        return heroGoesFirst;
    }
}
