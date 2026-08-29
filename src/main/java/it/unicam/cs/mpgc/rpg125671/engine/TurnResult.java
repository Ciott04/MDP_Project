package it.unicam.cs.mpgc.rpg125671.engine;

public record TurnResult(
       int damageToMonster,
       int damageToHero,
       int heroHealed,
       int bossHealed,
       CombatResult combatResult
) {}
