package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.Boss;
import it.unicam.cs.mpgc.rpg125671.model.HealingPotion;
import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.Monster;
import it.unicam.cs.mpgc.rpg125671.model.Warrior;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatEngineTest {
    
    @Test
    @DisplayName("Il costruttore lancia eccezione se i parametri non sono validi")
    void testCostruttoreConParametriNonValidi() {
        Hero hero = new Warrior("Eroe");
        Monster monster = new Monster("Mostro", 50, 10, 5, 5, 20);
        
        assertThrows(IllegalArgumentException.class, () -> new CombatEngine(null, monster));
        assertThrows(IllegalArgumentException.class, () -> new CombatEngine(hero, null));
        
        hero.takeDamage(999);
        assertThrows(IllegalArgumentException.class, () -> new CombatEngine(hero, monster));
        
        Hero validHero = new Warrior("Eroe Valido");
        monster.takeDamage(999);
        assertThrows(IllegalArgumentException.class, () -> new CombatEngine(validHero, monster));
    }

    @Test
    @DisplayName("L'eroe attacca il mostro e calcola correttamente i danni")
    void testEroeAttaccaMostroDannoCalcolato() {
        Hero hero = new Warrior("Eroe"); // HP=120, ATK=15, DEF=15, SPD=5
        Monster monster = new Monster("Mostro", 50, 10, 5, 1, 20); // SPD=1 (eroe primo)
        CombatEngine engine = new CombatEngine(hero, monster);
        
        TurnResult result = engine.executeTurn(CombatAction.ATTACK);
        
        assertEquals(10, result.damageToMonster()); // 15 ATK - 5 DEF
        assertTrue(engine.isHeroFirst());
    }

    @Test
    @DisplayName("Il mostro attacca l'eroe se è ancora vivo")
    void testMostroAttaccaEroeSeVivo() {
        Hero hero = new Warrior("Eroe"); // ATK=15, DEF=15, HP=120
        Monster monster = new Monster("Mostro", 50, 20, 5, 1, 20); // ATK=20 (danno 5)
        CombatEngine engine = new CombatEngine(hero, monster);
        
        TurnResult result = engine.executeTurn(CombatAction.ATTACK);
        
        assertEquals(5, result.damageToHero()); // 20 ATK - 15 DEF
        assertEquals(115, hero.getCurrentHp());
    }

    @Test
    @DisplayName("La velocità determina l'ordine dei turni")
    void testVelocitaDeterminaOrdine() {
        Hero heroLento = new Warrior("Lento"); // SPD=5
        Monster monsterVeloce = new Monster("Veloce", 50, 10, 5, 10, 20);
        CombatEngine engine1 = new CombatEngine(heroLento, monsterVeloce);
        assertFalse(engine1.isHeroFirst());
        
        Hero heroVeloce = new Warrior("Veloce"); // SPD=5
        Monster monsterLento = new Monster("Lento", 50, 10, 5, 3, 20);
        CombatEngine engine2 = new CombatEngine(heroVeloce, monsterLento);
        assertTrue(engine2.isHeroFirst());
    }

    @Test
    @DisplayName("Il combattimento termina con HERO_WON se il mostro muore")
    void testCombattimentoTerminaConVittoriaEroe() {
        Hero hero = new Warrior("Eroe"); // ATK=15
        Monster monster = new Monster("Debole", 10, 10, 5, 1, 20); // hp=10, 15-5=10 danno
        CombatEngine engine = new CombatEngine(hero, monster);
        
        TurnResult result = engine.executeTurn(CombatAction.ATTACK);
        
        assertEquals(CombatResult.HERO_WON, result.combatResult());
        assertFalse(monster.isAlive());
        assertTrue(engine.isCombatOver());
    }

    @Test
    @DisplayName("Il combattimento termina con HERO_LOST se l'eroe muore")
    void testCombattimentoTerminaConSconfittaEroe() {
        Hero hero = new Warrior("Debole"); // HP=120, DEF=15
        Monster monster = new Monster("Forte", 500, 200, 5, 10, 20); // SPD=10, ATK=200
        CombatEngine engine = new CombatEngine(hero, monster); // Mostro attacca prima
        
        TurnResult result = engine.executeTurn(CombatAction.ATTACK);
        
        assertEquals(CombatResult.HERO_LOST, result.combatResult());
        assertFalse(hero.isAlive());
        assertTrue(engine.isCombatOver());
    }

    @Test
    @DisplayName("L'eroe guadagna exp dal mostro in caso di vittoria")
    void testEroeGuadagnaExpInVittoria() {
        Hero hero = new Warrior("Eroe"); // EXP=0
        Monster monster = new Monster("Mostro", 10, 10, 5, 1, 50); // reward=50
        CombatEngine engine = new CombatEngine(hero, monster);
        
        engine.executeTurn(CombatAction.ATTACK); // Uccide il mostro
        
        assertEquals(50, hero.getCurrentExp());
    }

    @Test
    @DisplayName("Azione USE_POTION: l'eroe si cura, il mostro attacca comunque")
    void testUsoPozioneEroeSiCuraEMostroAttacca() {
        Hero hero = new Warrior("Eroe"); 
        hero.takeDamage(50); // HP scende a 70
        hero.getInventory().addItem(new HealingPotion());
        Monster monster = new Monster("Mostro", 50, 20, 5, 1, 20); // Danno 5
        CombatEngine engine = new CombatEngine(hero, monster);
        
        TurnResult result = engine.executeTurn(CombatAction.USE_POTION);
        
        assertEquals(30, result.heroHealed()); // Pozione cura 30
        assertEquals(5, result.damageToHero());
        assertEquals(95, hero.getCurrentHp()); // 70 + 30 - 5 = 95
    }

    @Test
    @DisplayName("USE_POTION senza pozioni lancia IllegalStateException")
    void testUsoPozioneSenzaPozioniLanciaEccezione() {
        Hero hero = new Warrior("Eroe"); 
        Monster monster = new Monster("Mostro", 50, 10, 5, 1, 20);
        CombatEngine engine = new CombatEngine(hero, monster);
        
        assertThrows(IllegalStateException.class, () -> engine.executeTurn(CombatAction.USE_POTION));
    }

    @Test
    @DisplayName("Il boss attiva la cura di emergenza durante il combattimento")
    void testBossCuraDiEmergenza() {
        Hero hero = new Warrior("Eroe"); // SPD=5
        // Boss HP 200, THRESHOLD 30% = 60, HEAL_PERCENTAGE 20% = 40
        Boss boss = new Boss("Boss", 200, 10, 100, 1, 100); 
        CombatEngine engine = new CombatEngine(hero, boss); // Eroe attacca prima, no danno (max 1)
        
        // Riduce manualmente la vita del boss per innescare la cura
        boss.takeDamage(145); // HP=55 (< 60)
        
        TurnResult result = engine.executeTurn(CombatAction.ATTACK); // ATK 15 - 100 DEF = 1 danno. HP=54 prima della cura
        
        assertTrue(result.bossHealed() > 0);
        assertEquals(40, result.bossHealed());
        assertEquals(94, boss.getCurrentHp());
    }

    @Test
    @DisplayName("executeTurn dopo la fine del combattimento lancia eccezione")
    void testExecuteTurnDopoFineCombattimentoLanciaEccezione() {
        Hero hero = new Warrior("Eroe");
        Monster monster = new Monster("Mostro", 1, 10, 5, 1, 20);
        CombatEngine engine = new CombatEngine(hero, monster);
        
        engine.executeTurn(CombatAction.ATTACK); // Uccide il mostro
        
        assertThrows(IllegalArgumentException.class, () -> engine.executeTurn(CombatAction.ATTACK));
    }
}
