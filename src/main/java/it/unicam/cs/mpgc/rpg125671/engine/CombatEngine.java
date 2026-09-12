package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.*;

/**
 * Gestisce la logica di un singolo combattimento a turni tra un {@link Hero} e un {@link Monster}.
 * L'ordine dei turni è determinato dalla velocità: chi ha velocità maggiore attacca per primo.
 * Il boss può interrompersi per curarsi una volta sola (vedi {@link Boss#tryEmergencyHeal()}).
 */
public class CombatEngine {

    private final Hero hero;
    private final Monster monster;
    private final boolean heroGoesFirst;

    /**
     * Inizializza il combattimento tra un eroe e un mostro.
     * Determina immediatamente l'ordine dei turni in base alla velocità.
     *
     * @param hero    l'eroe partecipante; non può essere null o morto.
     * @param monster il mostro partecipante; non può essere null o morto.
     * @throws IllegalArgumentException se uno dei parametri non è valido.
     */
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

    /**
     * Esegue un turno di combattimento con l'azione scelta dall'eroe.
     * Il flusso dipende dall'ordine dei turni:
     * <ul>
     *   <li>Se l'eroe va prima: esegue la sua azione, poi il boss tenta la cura, poi il mostro attacca.</li>
     *   <li>Se il mostro va prima: il mostro attacca, poi (se l'eroe è vivo) l'eroe esegue la sua azione.</li>
     * </ul>
     * Se il mostro muore, l'eroe guadagna l'exp e può salire di livello.
     *
     * @param heroAction l'azione scelta dal giocatore ({@link CombatAction#ATTACK} o {@link CombatAction#USE_POTION}).
     * @return il risultato del turno con danni, cure e stato del combattimento.
     * @throws IllegalStateException se il combattimento è già terminato.
     */
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

        LevelUpInfo levelUpInfo = null;
        if (hero.isAlive() && !monster.isAlive()) {
            int levelBefore = hero.getLevel();
            int hpBefore    = hero.getMaxHp();
            int atkBefore   = hero.getAttack();
            int defBefore   = hero.getDefense();
            int spdBefore   = hero.getSpeed();

            hero.gainExp(monster.getExpReward());

            if (hero.getLevel() > levelBefore) {
                levelUpInfo = new LevelUpInfo(
                        hero.getLevel(),
                        hero.getMaxHp()    - hpBefore,
                        hero.getAttack()   - atkBefore,
                        hero.getDefense()  - defBefore,
                        hero.getSpeed()    - spdBefore
                );
            }
        }

        return new TurnResult(damageToMonster, damageToHero, heroHealed, bossHealed, getCombatResult(), levelUpInfo);
    }

    private int applyAttack(Combatant attacker, Combatant defender) {
        int damage = Math.max(1, attacker.getAttack() - defender.getDefense() / 2);
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
