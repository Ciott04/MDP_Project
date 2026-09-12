package it.unicam.cs.mpgc.rpg125671.model;

import java.util.*;

/**
 * Gestisce la collezione di oggetti ({@link Item}) dell'eroe.
 * Gli oggetti sono raggruppati per nome in una mappa {@code nome → lista},
 * consentendo di avere più copie dello stesso oggetto.
 */
public class Inventory {

    private final Map<String, List<Item>> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    /**
     * Aggiunge un oggetto all'inventario.
     * Se il tipo (per nome) è già presente, l'oggetto viene accodato.
     *
     * @param item l'oggetto da aggiungere; non può essere null.
     */
    public void addItem(Item item) {
        items.computeIfAbsent(item.getName(), k -> new ArrayList<>()).add(item);
    }

    /**
     * Rimuove e restituisce un oggetto del tipo specificato.
     *
     * @param itemName il nome dell'oggetto da rimuovere.
     * @return l'oggetto rimosso.
     * @throws IllegalArgumentException se nessun oggetto con quel nome è presente.
     */
    public Item removeItem(String itemName) {
        List<Item> stack = items.get(itemName);
        if (stack == null || stack.isEmpty())
            throw new IllegalArgumentException("Oggetto non presente nell'inventario");
        Item removed = stack.removeLast();
        if (stack.isEmpty())
            items.remove(itemName);
        return removed;
    }

    /**
     * @param itemName il nome dell'oggetto.
     * @return {@code true} se almeno una copia dell'oggetto è presente.
     */
    public boolean hasItem(String itemName) {
        List<Item> stack = items.get(itemName);
        return stack != null && !stack.isEmpty();
    }

    /**
     * @param itemName il nome dell'oggetto.
     * @return il numero di copie dell'oggetto presenti; {@code 0} se assente.
     */
    public int getItemCount(String itemName) {
        List<Item> stack = items.get(itemName);
        return stack == null ? 0 : stack.size();
    }

    /**
     * Restituisce una vista immutabile del contenuto dell'inventario
     * come mappa {@code nome → quantità}. Usato per la serializzazione.
     *
     * @return mappa non modificabile del contenuto.
     */
    public Map<String, Integer> getSummary() {
        Map<String, Integer> summary = new HashMap<>();
        for (Map.Entry<String, List<Item>> entry : items.entrySet())
            summary.put(entry.getKey(), entry.getValue().size());
        return Map.copyOf(summary);
    }

}
