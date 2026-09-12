package it.unicam.cs.mpgc.rpg125671.model;

/**
 * Oggetto usabile dall'eroe durante la partita.
 * Nuovi tipi di oggetto possono essere aggiunti implementando questa interfaccia,
 * senza modificare il codice esistente (OCP).
 */
public interface Item {

    /** @return il nome dell'oggetto, usato come chiave nell'inventario. */
    String getName();

    /**
     * Applica l'effetto dell'oggetto all'eroe.
     *
     * @param hero l'eroe su cui applicare l'effetto.
     */
    void use(Hero hero);
}
