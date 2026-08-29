package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.*;

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
            bossHealed = applyBossHeal();
            if (monster.isAlive())
                damageToHero = applyAttack(monster, hero);
        }
        else {
            damageToHero = applyAttack(monster, hero);
            if (hero.isAlive()) {
                if (heroAction == CombatAction.ATTACK)
                    damageToMonster = applyAttack(hero, monster);
                else
                    heroHealed = applyPotion();
                bossHealed = applyBossHeal();
            }
        }

        if (hero.isAlive() && !monster.isAlive())
            hero.gainExp(monster.getExpReward());

        return new TurnResult(damageToMonster, damageToHero, heroHealed, bossHealed, getCombatResult());
    }

    private int applyAttack(Combatant attacker, Combatant defender) {
        int damage = Math.max(1, attacker.getAttack() - defender.getDefense());
        defender.takeDamage(damage);
        return damage;
    }

    private int applyPotion() {
        String potionName = "Pozione curativa";
        if (!hero.getInventory().hasItem(potionName))
            throw new IllegalStateException(potionName + " non disponibile nell'inventario.");
        int hpBefore = hero.getCurrentHp();
        Item potion = hero.getInventory().removeItem(potionName);
        potion.use(hero);
        return hero.getCurrentHp() - hpBefore;
    }

    private int applyBossHeal() {
        if (monster instanceof Boss boss) {
            int hpBefore = boss.getCurrentHp();
            if (boss.tryEmergencyHeal())
                return boss.getCurrentHp() - hpBefore;
        }
        return 0;
    }

    private CombatResult getCombatResult() {
        if (!hero.isAlive()) return CombatResult.HERO_LOST;
        if (!monster.isAlive()) return CombatResult.HERO_WON;
        return CombatResult.IN_PROGRESS;
    }

    public boolean isCombatOver() {
        return !hero.isAlive() || !monster.isAlive();
    }

    public boolean isHeroFirst() {
        return heroGoesFirst;
    }
}
