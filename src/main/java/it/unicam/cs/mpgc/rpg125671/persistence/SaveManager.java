package it.unicam.cs.mpgc.rpg125671.persistence;

import java.io.IOException;

/**
 * Contratto per la persistenza dello stato di gioco.
 * L'implementazione concreta ({@link JsonSaveManager}) può essere sostituita
 * con altre strategie (database, cloud, ecc.) senza modificare il codice chiamante (OCP).
 */
public interface SaveManager {

    /**
     * Serializza e salva lo stato di gioco.
     *
     * @param gameSave lo stato da salvare.
     * @param fileName il nome logico del file di salvataggio (senza estensione).
     * @throws IOException se il salvataggio fallisce.
     */
    void save(GameSave gameSave, String fileName) throws IOException;

    /**
     * Carica e deserializza uno stato di gioco precedentemente salvato.
     *
     * @param fileName il nome logico del file di salvataggio.
     * @return lo stato caricato.
     * @throws IOException se il file non esiste o il caricamento fallisce.
     */
    GameSave load(String fileName) throws IOException;

    /**
     * Verifica se esiste un salvataggio con il nome specificato.
     *
     * @param fileName il nome logico del file di salvataggio.
     * @return {@code true} se il salvataggio esiste.
     */
    boolean saveExists(String fileName);
}
