package it.unicam.cs.mpgc.rpg125671.engine;

import it.unicam.cs.mpgc.rpg125671.model.GameMap;

/**
 * Strategia di generazione della mappa di gioco.
 * Implementazioni alternative (es. caricamento da file) possono essere
 * aggiunte senza modificare il codice esistente (OCP).
 */
public interface MapGenerator {

    /**
     * Genera e restituisce una nuova {@link GameMap}.
     *
     * @return la mappa generata.
     */
    GameMap generate();
}
