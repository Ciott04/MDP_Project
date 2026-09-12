package it.unicam.cs.mpgc.rpg125671.engine;

public record LevelUpInfo(
        int newLevel,
        int hpGain,
        int attackGain,
        int defenseGain,
        int speedGain
) {}
