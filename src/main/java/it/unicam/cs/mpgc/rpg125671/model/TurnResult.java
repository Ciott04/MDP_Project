package it.unicam.cs.mpgc.rpg125671.model;

public record TurnResult(
       int damageToMonster,
       int damageToHero,
       int heroHealed,
       int bossHealed,
       CombatResult combatResult
) {}
